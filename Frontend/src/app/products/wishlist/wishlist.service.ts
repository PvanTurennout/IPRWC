import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthenticationService} from '../../authentication/authentication.service';
import {environment} from '../../../environments/environment';
import {catchError, tap} from 'rxjs/operators';
import {handleHttpErrors} from '../../typescript/utils/http-error-handeling';

@Injectable({providedIn: 'root'})
export class WishlistService{

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService,
  ) {}

  remove(productId: number) {
    const options = {
      headers: new HttpHeaders({'Content-Type': 'application/json',}), body: +productId
    };

    return this.http.delete(environment.uriPrefix + 'wishlist', options).pipe(
      catchError( err => handleHttpErrors(err)),
      tap(() => {
        this.authService.removeProductFromWishlist(productId);
      })
    );
  }

  add(productId: number) {
    return this.http.put(environment.uriPrefix + 'wishlist', +productId).pipe(
      catchError(err => handleHttpErrors(err)),
      tap( () => {
        this.authService.addProductToWishlist(productId);
      })
    );
  }


}
