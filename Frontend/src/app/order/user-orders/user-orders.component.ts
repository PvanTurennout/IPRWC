import {Component, OnInit} from '@angular/core';
import {OrderService} from '../order.service';
import {Order} from '../../typescript/model/order.model';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.scss']
})
export class UserOrdersComponent implements OnInit {

  orderList: Order[];

  constructor(private orderService: OrderService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getUsersOrders();
  }

  getUsersOrders() {
    this.orderService.getUsersOrders().subscribe(
      response => this.orderList = response,
      err => displayHttpError(err, this.dialog)
    );
  }
}
