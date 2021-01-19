import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError, tap} from 'rxjs/operators';
import {BehaviorSubject, throwError} from 'rxjs';
import {User} from '../typescript/model/user.model';
import {Account} from '../typescript/model/account.model';
import {MatDialog} from '@angular/material/dialog';
import {WarningDialogComponent} from '../shared/dialogs/warning-dialog.component';
import {handleHttpAuthErrors} from '../typescript/utils/http-error-handeling';
import {Router} from '@angular/router';

interface TokenResponse {
  refreshToken: string;
  token: string;
}

interface JwtPayload {
  accountId: number;
  exp: number;
  group: number;
  wishlist: number[];
}

@Injectable({providedIn: 'root'})
export class AuthenticationService{

  static BASE_ENDPOINT = environment.uriPrefix + 'authentication';

  userSubject = new BehaviorSubject<User>(null);

  private user: User;
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private dialog: MatDialog, private router: Router) {
  }

  addProductToWishlist(productId: number){
    this.user.wishlist.push(productId);
    this.emitUser();
  }

  removeProductFromWishlist(productId: number) {
    this.user.wishlist.splice(this.user.wishlist.indexOf(productId), 1);
    this.emitUser();
  }

  login(account: Account, stayLoggedIn: boolean) {
    return this.http.post<TokenResponse>(AuthenticationService.BASE_ENDPOINT, account.toRequest())
      .pipe(catchError( err => throwError(handleHttpAuthErrors(err)) ),
      tap(tokens => {

        const payload = this.parseJWTPayload(tokens.token);

        this.user = new User(
          tokens.refreshToken,
          tokens.token,
          payload.exp,
          payload.group,
          payload.wishlist,
          payload.accountId
        );

        if (stayLoggedIn){
          localStorage.setItem('refreshToken', tokens.refreshToken);
        }
        this.activateRefreshTimer();
        this.emitUser();
      })
      );
  }

  private parseJWTPayload(jwt: string): JwtPayload {
    // atob() decodes bas64 string
    return JSON.parse(atob(jwt.split('.')[1]));
  }

  logout() {
    this.user = null;
    localStorage.removeItem('refreshToken');
    this.emitUser();
    this.router.navigate(['/']);
  }

  refresh() {
    return this.http.post<{freshToken: string}>(AuthenticationService.BASE_ENDPOINT + '/refresh', {refreshToken: this.user.refreshToken})
      .pipe(
        catchError(err => throwError(handleHttpAuthErrors(err))),
        tap(token => {
          const payload = this.parseJWTPayload(token.freshToken);

          this.user.token = token.freshToken;
          this.user.expirationDate = payload.exp;
          this.user.userId = payload.accountId;
          this.user.group = payload.group;
          this.user.wishlist = payload.wishlist;
          this.emitUser();
        })
      );
  }

  register(account: Account) {
    return this.http.post(environment.uriPrefix + 'account/register', account.toRequest())
      .pipe(
        catchError(err => handleHttpAuthErrors(err))
      );
  }

  private emitUser(){
    this.userSubject.next(this.user);
  }

  autoLogin(){
    const lsRefreshToken = localStorage.getItem('refreshToken');
    if (lsRefreshToken){
      this.user = new User(lsRefreshToken);
      this.refresh().subscribe(() => {this.activateRefreshTimer();}, () => {this.user = null;});
    }
  }

  activateRefreshTimer() {
    this.tokenExpirationTimer = setTimeout(() => {
      this.refresh().subscribe(
        () => {
          this.clearRefreshTimer();
          this.activateRefreshTimer();
        },
        () => {
          const dialog = this.dialog.open(WarningDialogComponent, {data: {warningMessage: 'Unable to validate token, you will be logged out.'}});
          dialog.afterClosed().subscribe(() => {this.logout();});
        }
      );
    }, this.user.getExpirationTime());
  }

  clearRefreshTimer(){
    if (this.tokenExpirationTimer){
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }
}
