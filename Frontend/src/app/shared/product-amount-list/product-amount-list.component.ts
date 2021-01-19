import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {environment} from '../../../environments/environment';
import {ShoppingCartItem} from '../../typescript/model/shoppingCartItem.model';

@Component({
  selector: 'app-product-amount-list',
  templateUrl: './product-amount-list.component.html',
  styleUrls: ['./product-amount-list.component.scss']
})
export class ProductAmountListComponent implements OnInit {

  @Input() totalCost: number;
  @Input() productList: ShoppingCartItem[];
  displayedColumns = ['image', 'name', 'amount', 'subtotal'];

  constructor() { }

  ngOnInit(): void {
    this.makeTableResponsive();
  }

  // Styling & Responsive
  shouldImagesBeHidden(){
    return window.innerWidth < 1200;
  }

  doesImageColumnExists(){
    return this.displayedColumns.indexOf('image') !== -1;
  }

  makeTableResponsive(){
    const imageIndex = 0;

    if(this.shouldImagesBeHidden()){
      if (!this.doesImageColumnExists()){
        return;
      }

      this.displayedColumns.splice(imageIndex, 1);

    } else {
      if (this.doesImageColumnExists()){
        return;
      }

      this.displayedColumns.splice(imageIndex, 0, 'image');
    }
  }

  getImagePath(product: Product){
    return environment.imageUrlString + product.imagePath;
  }
}
