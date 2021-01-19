import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {Router} from '@angular/router';
import {AddToCartDialogComponent} from '../dialogs/add-to-cart-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {ShoppingCartService} from '../../shopping-cart/shopping-cart.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent implements OnInit {

  @Input() product: Product;

  constructor(private router: Router, private dialog: MatDialog, private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
  }

  getProductImagePath(): string {
    return environment.imageUrlString + this.product.imagePath;
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

  goToProductPage() {
    this.router.navigate(['/product/' + this.product.productId]).then(() => {window.scrollTo(0, 0);});
  }

}
