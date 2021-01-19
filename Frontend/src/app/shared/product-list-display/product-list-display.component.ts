import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../typescript/model/product.model';
import {Columns} from '../../typescript/enums/columns.enum';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ConfirmWarnDialogComponent} from '../dialogs/confirm-warn-dialog.component';
import {AddToCartDialogComponent} from '../dialogs/add-to-cart-dialog.component';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {WishlistService} from '../../products/wishlist/wishlist.service';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';
import {ProductService} from '../../products/product.service';

@Component({
  selector: 'app-product-list-display',
  templateUrl: './product-list-display.component.html',
  styleUrls: ['./product-list-display.component.scss']
})
export class ProductListDisplayComponent implements OnInit {

  @Input() products: Product[];
  @Input() columns: Columns[];

  dataSource = new MatTableDataSource<Product>();

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private wishlistService: WishlistService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.refresh();
    this.makeTableResponsive();
  }

  deleteFromWishlist(index: number, dialogRef: MatDialogRef<ConfirmWarnDialogComponent>){
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.wishlistService.remove(this.products[index].productId).subscribe(
          () => this.removeAndRefresh(index),
          error => displayHttpError(error, this.dialog)
        );
      }
    });
  }

  deleteDialog(index: number, wishlist: boolean) {
    const dialogRef = this.dialog.open(ConfirmWarnDialogComponent, {
        data: {
          title: 'Delete Item',
          text: 'Are You Sure You Want To Delete This Item?',
          buttonText: 'Delete'
        }
      }
    );

    if (wishlist){
      this.deleteFromWishlist(index, dialogRef);
    } else {
      this.deleteProduct(this.products[index].productId, dialogRef, index);
    }
  }

  deleteProduct(productId: number, dialogRef: MatDialogRef<ConfirmWarnDialogComponent>, index: number) {
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productService.deleteProduct(productId).subscribe(
          () => this.removeAndRefresh(index),
            error => displayHttpError(error, this.dialog));
      }
    });
  }

  removeAndRefresh(index: number) {
    this.products.splice(index, 1);
    this.refresh();
  }

  addToCart(index: number){
    const dialogRef = this.dialog.open(AddToCartDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      this.deleteDialog(index, true);

      if (result) {
        this.router.navigate(['/checkout']);
      }
    });
  }

  editProduct(productId: number){
    this.router.navigate(['/edit/product/' + productId]);
  }

  makeTableResponsive(){
    const imageIndex = 0;

    if(this.shouldImagesBeHidden()){
      if (!this.doesImageColumnExists()){
        return;
      }

      this.columns.splice(imageIndex, 1);

    } else {
      if (this.doesImageColumnExists()){
        return;
      }

      this.columns.splice(imageIndex, 0, Columns.IMAGE);
    }
  }

  shouldImagesBeHidden(){
    return window.innerWidth < 1200;
  }

  doesImageColumnExists(){
    return this.columns.indexOf(Columns.IMAGE) !== -1;
  }

  getImagePath(product: Product){
    return environment.imageUrlString + product.imagePath;
  }

  refresh() {
    this.dataSource.data = this.products;
  }


}
