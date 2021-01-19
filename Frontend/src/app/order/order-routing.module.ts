import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {OrderComponent} from './order/order.component';
import {PaymentPageComponent} from './payment-placeholder-pages/payment-page.component';
import {UserOrdersComponent} from './user-orders/user-orders.component';
import {AuthGuard} from '../typescript/guards/auth.guard';

const routes: Routes = [
  {path: 'checkout', canActivate: [AuthGuard], component: OrderComponent},
  {path: 'pay/paypal', component: PaymentPageComponent, data: {method: 'PayPal'}},
  {path: 'pay/creditcard', component: PaymentPageComponent, data: {method: 'Credit Card'}},
  {path: 'my-orders', canActivate: [AuthGuard], component: UserOrdersComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
