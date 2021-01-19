import { Component, OnInit } from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {environment} from '../../../environments/environment';
import {ActivatedRoute, Router, Data} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {ShoppingCartService} from '../../shopping-cart/shopping-cart.service';
import {AddToCartDialogComponent} from '../../shared/dialogs/add-to-cart-dialog.component';
import {AuthenticationService} from '../../authentication/authentication.service';
import {ConfirmWarnDialogComponent} from '../../shared/dialogs/confirm-warn-dialog.component';
import {WishlistService} from '../wishlist/wishlist.service';
import {displayHttpError, emptyFunction} from '../../typescript/utils/http-error-handeling';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.scss']
})
export class ProductPageComponent implements OnInit {

  productId: number;
  product: Product;
  inWishlist: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private shoppingCartService: ShoppingCartService,
    private authService: AuthenticationService,
    private wishlistService: WishlistService
  ) { }

  ngOnInit(): void {
    this.grabProductId();
    this.getProductObservable();
    this.isWished();
  }

  grabProductId(){
    this.productId = this.route.snapshot.params.id;
  }

  getProductObservable(){
    this.route.data.subscribe(
      (data: Data) => {
        this.product = data.productResolver;
      }
    );
  }

  addToCart(){
    this.shoppingCartService.add(this.product);
    const dialogRef = this.dialog.open(AddToCartDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.router.navigate(['/checkout']);
      }
    });
  }

  isWished() {
    if (this.isLoggedIn()) {
      this.authService.userSubject.subscribe(
        user => {
          this.inWishlist = user.isProductWished(this.productId);
        }
      );
    } else {
      return false;
    }
  }

  isLoggedIn() {
    return !!this.authService.userSubject.value;
  }

  onWishlist() {
    if (this.inWishlist) {
      this.removeFromWishlist();
    } else {
      this.addToWishlist();
    }
    this.isWished();
  }

  addToWishlist() {
    this.wishlistService.add(this.productId).subscribe(
      emptyFunction,
      error => displayHttpError(error, this.dialog)
    );
  }

  removeFromWishlist() {
    const dialogRef = this.dialog.open(ConfirmWarnDialogComponent, {
        data: {
          title: 'Delete Item',
          text: 'Are You Sure You Want To Delete This Item?',
          buttonText: 'Delete'
        }
      }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.wishlistService.remove(this.productId).subscribe(
          emptyFunction,
          error => displayHttpError(error, this.dialog)
        );
      }
    });
  }

  getProductImagePath() {
    return environment.imageUrlString + this.product.imagePath;
  }

}
