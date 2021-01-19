import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../typescript/model/product.model';

@Component({
  selector: 'app-product-card-box',
  templateUrl: './product-card-box.component.html',
  styleUrls: ['./product-card-box.component.scss']
})
export class ProductCardBoxComponent implements OnInit {

  @Input() productList: Product[];

  constructor() { }

  ngOnInit(): void {
  }

}
