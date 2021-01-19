import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import {SharedModule} from '../shared/shared.module';
import {ShoppingCartRoutingModule} from './shopping-cart-routing.module';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatTableModule} from '@angular/material/table';



@NgModule({
  declarations: [ShoppingCartComponent],
  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule,
    SharedModule,
    ShoppingCartRoutingModule
  ]
})
export class ShoppingCartModule { }
