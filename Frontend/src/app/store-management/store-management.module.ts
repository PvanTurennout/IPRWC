import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StoreManagementRoutingModule } from './store-management-routing.module';
import { StoreManagementComponent } from './store-management/store-management.component';
import {MatTabsModule} from '@angular/material/tabs';
import {SharedModule} from '../shared/shared.module';
import {OrderManagementComponent} from './order-management/order-management.component';
import {ProductManagementComponent} from './product-management/product-management.component';
import {ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { EditProductComponent } from './edit-product/edit-product.component';


@NgModule({
  declarations: [
    StoreManagementComponent,
    OrderManagementComponent,
    ProductManagementComponent,
    EditProductComponent
  ],
  imports: [
    CommonModule,
    StoreManagementRoutingModule,
    MatTabsModule,
    SharedModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule
  ]
})
export class StoreManagementModule { }
