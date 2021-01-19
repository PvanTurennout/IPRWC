import {Component} from '@angular/core';

@Component({
  selector: 'app-add-to-cart-dialog',
  template: `
    <h2 mat-dialog-title>Add Item To Cart</h2>
    <mat-dialog-content class="mat-typography">
      <p>You Have Added This Item To The Shopping Cart</p>
    </mat-dialog-content>
    <mat-dialog-actions>
      <button mat-raised-button mat-dialog-close color="primary">Continue Shopping</button>
      <button mat-raised-button [mat-dialog-close]="false" routerLink="/cart" color="primary">Shopping Cart</button>
      <button mat-raised-button [mat-dialog-close]="true" color="accent">Checkout</button>
    </mat-dialog-actions>
  `,
  styles: [`

  `]
})
export class AddToCartDialogComponent {

}
