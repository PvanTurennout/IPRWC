import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {Columns} from '../../typescript/enums/columns.enum';
import {WishlistService} from './wishlist.service';
import {ProductListDisplayComponent} from '../../shared/product-list-display/product-list-display.component';
import {AuthenticationService} from '../../authentication/authentication.service';
import {ProductService} from '../product.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit, AfterViewInit {

  wishlist: Product[] = [];
  wishlistItemTableColumns: Columns[] = [Columns.IMAGE, Columns.NAME, Columns.CART, Columns.DELETEWISHLIST];
  @ViewChild(ProductListDisplayComponent, {static: false}) table;

  constructor(
    private wishlistService: WishlistService,
    private authService: AuthenticationService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.getWishlist();
  }

  getWishlist() {
    const idList = this.authService.userSubject.value.wishlist;
    if (idList.length < 1){
      this.wishlist = [];
    } else {
      this.productService.getSpecifiedProductList(idList).subscribe(
        products => {this.wishlist = products;}
      );
    }
  }

  ngAfterViewInit() {
    setTimeout(() => {this.table.refresh();}, 0);
  }

}
