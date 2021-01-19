import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from '../../authentication/authentication.service';
import {ConfirmWarnDialogComponent} from '../../shared/dialogs/confirm-warn-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {Group} from '../../typescript/enums/group.enum';
import {ShoppingCartService} from '../../shopping-cart/shopping-cart.service';

@Component({
  selector : 'app-navigation',
  styles: [
    `
      a {
        display: inline-block;
        margin: 0 10px;
        color: white;
        text-decoration: none;

      }
    `
  ],
  template : `
    <a mat-list-item class="underline-hover" routerLink="/watches">Watches</a>
    <a mat-list-item class="underline-hover" routerLink="/login" *ngIf="!loggedIn; else loggedInNav">Login</a>
    <a mat-list-item class="underline-hover" routerLink="/cart">
      <button mat-icon-button>
        <mat-icon [matBadge]="itemsInCart"
                  matBadgeColor="accent"
                  matBadgeSize="small"
                  [matBadgeHidden]="itemsInCart < 1"
        >shopping_cart</mat-icon>
      </button>
    </a>

    <ng-template #loggedInNav>
      <a mat-list-item class="underline-hover">
        <button mat-icon-button [matMenuTriggerFor]="loggedInMenu"> <mat-icon>account_circle</mat-icon> <mat-icon>arrow_drop_down</mat-icon></button>
      </a>
    </ng-template>

    <mat-menu #loggedInMenu="matMenu" xPosition="before">
      <button mat-menu-item routerLink="/wishlist"> <mat-icon>favorite</mat-icon> <span>Wishlist</span></button>
      <button mat-menu-item routerLink="/my-orders"> <mat-icon>receipt</mat-icon> <span>Orders</span></button>
      <button mat-menu-item *ngIf="storeRole" routerLink="/store"> <mat-icon>admin_panel_settings</mat-icon> <span>Store Page</span></button>
      <button mat-menu-item (click)="logout()"> <mat-icon>exit_to_app</mat-icon> Logout</button>
    </mat-menu>
  `
})
export class NavigationComponent implements OnInit, OnDestroy{
  loggedIn = false;
  storeRole = false;
  itemsInCart: number;

  constructor(private authService: AuthenticationService,
              private dialog: MatDialog,
              private shoppingCartService: ShoppingCartService
  ) { }

  ngOnInit() {
    this.userSubscription();
    this.cartSubscription();
  }

  cartSubscription() {
    this.shoppingCartService.shoppingCartSubject.subscribe(
      cart => {
        this.itemsInCart = cart.length;
      }
    );
  }

  userSubscription() {
    this.authService.userSubject.subscribe(user => {
      this.loggedIn = user !== null;
      if (this.loggedIn) {
        this.storeRole = user.isInGroup(Group.ADMIN) || user.isInGroup(Group.SELLER);
      }
    });
  }

  logout() {
    const dialogRef = this.dialog.open(ConfirmWarnDialogComponent, {
      data: {
        title: 'Logout',
        text: 'Are You Sure You Want To Logout?',
        buttonText: 'Logout'
      }
    }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.authService.logout();
      }
    });

  }

  ngOnDestroy() {
    this.authService.userSubject.unsubscribe();
  }

}
