import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { OrderComponent } from './order/order.component';
import {MatStepperModule} from '@angular/material/stepper';
import {ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {SharedModule} from '../shared/shared.module';
import {MatRadioModule} from '@angular/material/radio';
import {PaymentPageComponent} from './payment-placeholder-pages/payment-page.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';


@NgModule({
  declarations: [
      OrderComponent,
      PaymentPageComponent,
      UserOrdersComponent
  ],
    imports: [
        CommonModule,
        OrderRoutingModule,
        MatStepperModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatInputModule,
        SharedModule,
        MatRadioModule
    ]
})
export class OrderModule { }
