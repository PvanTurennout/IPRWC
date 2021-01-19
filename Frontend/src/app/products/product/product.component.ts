import { Component, OnInit} from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {FormControl} from '@angular/forms';
import {ProductService} from '../product.service';
import {WarningDialogComponent} from '../../shared/dialogs/warning-dialog.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  products: Product[] = [];

  brandFilter = new FormControl([]);
  brands: string[] = [];
  searchFormControl = new FormControl('');
  minPrice: number;
  maxPrice: number;


  constructor(private productService: ProductService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getProductsObservable();

  }

  getProductsObservable(){
    this.productService.getAllProducts().subscribe(
      productList => {
        this.products = productList;
      },
      error => {
        this.dialog.open(WarningDialogComponent, {data: {warningMessage: error}});
      },
      () => {
        this.findBrandFilters();
        this.sortName(true);
      }
      );
  }

  findBrandFilters(){
    for (let product of this.products) {
      if (!(this.brands.indexOf(product.brand) >= 0)) {
        this.brands.push(product.brand);
      }
    }
  }

  sortPrice(isAscending: boolean) {
    const data = this.products.slice();

    this.products = data.sort((a, b) =>
      this.compare(a.price, b.price, isAscending)
    );
  }

  sortName(alphabetical: boolean) {
    const data = this.products.slice();

    this.products = data.sort((a, b) =>
      this.compare(a.name, b.name, alphabetical)
    );
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

}
