import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError, map} from 'rxjs/operators';
import {Order, OrderResponse} from '../typescript/model/order.model';
import {handleHttpErrors} from '../typescript/utils/http-error-handeling';
import {throwError} from 'rxjs';


@Injectable({providedIn: 'root'})
export class OrderService{

  private readonly uri = environment.uriPrefix + 'order';

  constructor(private http: HttpClient) {
  }

  createOrder(order: Order){
    return this.http.post(this.uri, order.toCreateRequest())
      .pipe(
        catchError(err => handleHttpErrors(err))
      );
  }

  getUsersOrders(){
    return this.getOrderList('/account');
  }

  getAllOrders(){
    return this.getOrderList('');
  }

  private getOrderList(endpoint: string) {
    return this.http.get<OrderResponse[]>(this.uri + endpoint)
      .pipe(
        catchError( err => throwError(handleHttpErrors(err))),
        map(response => this.mapOrderResponseList(response))
      );
  }

  private mapOrderResponseList(response: OrderResponse[]) {
    const orders: Order[] = [];

    response.map((orderResponse: OrderResponse) => orders.push(Order.fromResponse(orderResponse)));

    return orders;
  }

  updateOrderStatus(id: number, status: number) {
    return this.http.put(this.uri + '/status', {orderId: id, orderStatus: status})
      .pipe(
        catchError(err => throwError(handleHttpErrors(err)))
      );
  }

}
