import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { SharedModule } from '../shared/shared.module';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatSliderModule } from '@angular/material/slider';

import { ProductPageComponent } from './product-page/product-page.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { ProductsRoutingModule } from './products-routing.module';
import { ProductComponent } from './product/product.component';


@NgModule({
  declarations: [
    ProductComponent,
    ProductPageComponent,
    WishlistComponent
  ],
    imports: [
      MatInputModule,
      MatFormFieldModule,
      MatButtonModule,
      MatIconModule,
      MatMenuModule,
      MatSelectModule,
      MatSliderModule,
      CommonModule,
      ProductsRoutingModule,
      SharedModule,
      MatCardModule,
      ReactiveFormsModule,
        FormsModule
    ]
})
export class ProductsModule { }
