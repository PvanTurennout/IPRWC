import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Columns} from '../../typescript/enums/columns.enum';
import {ProductService} from '../../products/product.service';
import {Product} from '../../typescript/model/product.model';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';
import {MatDialog} from '@angular/material/dialog';
import {ProductListDisplayComponent} from '../../shared/product-list-display/product-list-display.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.scss']
})
export class ProductManagementComponent implements OnInit, AfterViewInit {

  products: Product[] = [];
  productListColumns = [Columns.IMAGE, Columns.NAME, Columns.EDIT, Columns.DELETE];
  @ViewChild(ProductListDisplayComponent, {static: false}) table;

  constructor(private productService: ProductService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getAllProducts().subscribe(
      response => this.products = response,
      error => displayHttpError(error, this.dialog)
    );
  }

  ngAfterViewInit() {
    setTimeout(() => {this.table.refresh();}, 100);
  }

  onNewProduct(){
    this.router.navigate(['/new/product']);
  }
}
