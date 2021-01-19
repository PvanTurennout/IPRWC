import { Component, OnInit } from '@angular/core';
import {OrderService} from '../../order/order.service';
import {MatDialog} from '@angular/material/dialog';
import {Order} from '../../typescript/model/order.model';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-order-management',
  templateUrl: './order-management.component.html',
  styleUrls: ['./order-management.component.scss']
})
export class OrderManagementComponent implements OnInit {

  orders: Order[];
  searchOrderFormControl = this.formBuilder.control('');

  constructor(private dialog: MatDialog, private orderService: OrderService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    setTimeout(() => this.getOrders(), 150);
  }

  getOrders() {
    this.orderService.getAllOrders().subscribe(
      response => this.orders = response,
      error => displayHttpError(error, this.dialog)
    );
  }

}
