import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthenticationService} from '../../authentication/authentication.service';
import {Group} from '../enums/group.enum';
import {WarningDialogComponent} from '../../shared/dialogs/warning-dialog.component';
import {MatDialog} from '@angular/material/dialog';

@Injectable({providedIn: 'root'})
export class SellerGuard implements CanActivate{

  constructor(private authService: AuthenticationService, private router: Router, private dialog: MatDialog) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree
  {
    const user = this.authService.userSubject.value;
    if (!user){
      return this.router.createUrlTree(['/']);
    }

    if (user.isInGroup(Group.SELLER) || user.isInGroup(Group.ADMIN)){
      return true;
    } else {
      this.dialog.open(WarningDialogComponent, {data: {warningMessage: 'You are not allowed to access this page!!!'}});
      return false;
    }
  }
}
