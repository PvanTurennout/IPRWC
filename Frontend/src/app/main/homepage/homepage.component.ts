import { Component, OnInit } from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {ProductService} from '../../products/product.service';
import {WarningDialogComponent} from '../../shared/dialogs/warning-dialog.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  highlightedProducts: Product[] = [];
  topProducts: Product[] = [];

  constructor(private productService: ProductService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.highlightedProductsSubscription();
    this.topSellingProductsSubscription();
  }

  highlightedProductsSubscription() {
    this.productService.getHighlightedProducts().subscribe(
      productList => {
        this.highlightedProducts = productList;
      },
      error => {
        this.dialog.open(WarningDialogComponent, {data: {warningMessage: error}});
      });
  }

  topSellingProductsSubscription() {
    this.productService.getBestSellingProducts().subscribe(
      productList => {
        this.topProducts = productList;
      },
      error => {
        this.dialog.open(WarningDialogComponent, {data: {warningMessage: error}});
      });
  }

}
