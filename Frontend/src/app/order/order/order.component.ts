import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Product} from '../../typescript/model/product.model';
import {Order} from '../../typescript/model/order.model';
import {Columns} from '../../typescript/enums/columns.enum';
import {Router} from '@angular/router';
import {ShoppingCartService} from '../../shopping-cart/shopping-cart.service';
import {ShoppingCartItem} from '../../typescript/model/shoppingCartItem.model';
import {OrderService} from '../order.service';
import {MatDialog} from '@angular/material/dialog';
import {WarningDialogComponent} from '../../shared/dialogs/warning-dialog.component';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  orderForm: FormGroup;
  customerInfo: FormGroup;
  shipping: FormGroup;
  shippingOptions = ['Standard', 'Express'];
  payment: FormGroup;
  paymentOptions = ['PayPal', 'CreditCard'];

  products: Product[] = [];
  itemTableColumns: Columns[] = [Columns.IMAGE, Columns.NAME];

  order: Order;
  list: ShoppingCartItem[] = [];
  cost: number;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private shoppingCartService: ShoppingCartService,
    private orderService: OrderService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.initForms();
    this.grabItems();
    this.populateProducts();
    this.grabCost();
  }

  grabCost() {
    this.cost = this.shoppingCartService.totalCost.value;
  }

  grabItems(){
    this.list = this.shoppingCartService.shoppingCartSubject.value;
  }

  populateProducts() {
    for (let item of this.list){
      this.products.push(item.product);
    }
  }

  initForms(): void{
    this.customerInfoFormInit();
    this.shippingInfoFormInit();
    this.paymentInfoFormInit();
    this.orderFormInit();
  }

  orderFormInit(): void {
    this.orderForm = this.formBuilder.group({
      customer: this.customerInfo,
      shippingMethod: this.shipping,
      paymentOption: this.payment
    });
  }

  customerInfoFormInit(): void {
    this.customerInfo = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength]],
      lastName: ['', [Validators.required, Validators.minLength]],
      address: ['', [Validators.required, Validators.minLength]],
      zipcode: ['', [Validators.required, Validators.pattern(/\d{4}([A-Z]{2}| [A-Z]{2})/)]],
      town: ['', [Validators.required, Validators.minLength]]
    });
  }

  shippingInfoFormInit(): void {
    this.shipping = this.formBuilder.group({method: ['', Validators.required]});
  }

  paymentInfoFormInit(): void {
    this.payment = this.formBuilder.group({paymentMethod: ['', Validators.required]});
  }

  onPayNow() {
    const customer = this.customerInfo.controls;
    const newOrder = new Order(this.list, customer.firstName.value, customer.lastName.value, customer.address.value,
      customer.zipcode.value, customer.town.value, this.shipping.controls.method.value, this.cost);

    this.orderService.createOrder(newOrder).subscribe(
      () => {
        const successDialog = this.dialog.open(WarningDialogComponent, {data: {warningMessage: 'You will be navigated to the payment page'}});
        successDialog.afterClosed().subscribe(() => this.router.navigate(['/pay/' + this.payment.controls.paymentMethod.value]));
      },
      error => this.dialog.open(WarningDialogComponent, {data: {warningMessage: error}}));
  }
}
