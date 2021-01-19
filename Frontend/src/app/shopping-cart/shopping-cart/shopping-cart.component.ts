import {Component, OnDestroy, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Product} from '../../typescript/model/product.model';
import {Router} from '@angular/router';
import {ConfirmWarnDialogComponent} from '../../shared/dialogs/confirm-warn-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {ShoppingCartService} from '../shopping-cart.service';
import { Subscription } from 'rxjs';
import {WarningDialogComponent} from '../../shared/dialogs/warning-dialog.component';

@Component({
  selector: 'app-shopping-card',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit, OnDestroy {

  totalCost: number;
  totalCostSubscription: Subscription;
  displayedColumns = ['image', 'name', 'amount', 'subtotal', 'delete'];

  constructor(private router: Router, private dialog: MatDialog, private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.totalCostSetter();
    this.makeTableResponsive();
  }

  ngOnDestroy() {
    this.totalCostSubscription.unsubscribe();
  }


  // Cart Operations
  upItemAmount(index: number){
    this.shoppingCartService.upAmount(index);
  }

  lowerItemAmount(index: number){
    try{
      this.shoppingCartService.lowerAmount(index);
    } catch (CantBeNullException){
        this.deleteItem(index);
    }
  }

  deleteItem(index: number){
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
        this.shoppingCartService.remove(index);
      }
    });
  }

  clearList(){
    this.shoppingCartService.empty();
  }


  makeOrder(){
    if (this.shoppingCartService.isEmpty()) {
      this.dialog.open(WarningDialogComponent, {data: {warningMessage: 'You can\'t make an order if you have no items'}});
      return;
    }

    this.router.navigate(['/checkout']);
  }

  shoppingCartObservable(){
    return this.shoppingCartService.shoppingCartSubject;
  }

  totalCostSetter(){
    this.totalCostSubscription = this.shoppingCartService.totalCost.subscribe(cost => this.totalCost = cost);
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
