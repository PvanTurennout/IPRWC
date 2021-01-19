import {Component, Input, OnInit} from '@angular/core';
import {Order} from '../../typescript/model/order.model';
import {getOrderStatusString, OrderStatus} from '../../typescript/enums/order-status.enum';
import {OrderService} from '../../order/order.service';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {

  @Input() canEdit: boolean;
  @Input() orders: Order[];

  statuses = [OrderStatus.received, OrderStatus.processed, OrderStatus.send, OrderStatus.delivered, OrderStatus.archived];

  constructor(private orderService: OrderService, private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  statusToString(status: OrderStatus) {
    return getOrderStatusString(status);
  }

  changeStatus(orderIndex: number, status: OrderStatus){
    this.orderService.updateOrderStatus(this.orders[orderIndex].orderId, status).subscribe(
      () => this.orders[orderIndex].orderStatus = status,
      error => displayHttpError(error, this.dialog)
    );
  }

}
