import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';

import { StringFilterPipe } from './pipes/string-filter.pipe';
import { StringListFilterPipe } from './pipes/string-list-filter.pipe';
import { PriceFilterPipe } from './pipes/price-filter.pipe';
import { AddToCartDialogComponent } from './dialogs/add-to-cart-dialog.component';
import { ConfirmWarnDialogComponent } from './dialogs/confirm-warn-dialog.component';
import { ProductCardBoxComponent } from './product-card-box/product-card-box.component';
import { ProductListDisplayComponent } from './product-list-display/product-list-display.component';
import { PageTitleComponent } from './style-components/page-title.component';
import { StandardDividerComponent } from './style-components/standard-divider.component';
import { ProductCardComponent } from './product-card/product-card.component';
import {WarningDialogComponent} from './dialogs/warning-dialog.component';
import { OrderListComponent } from './order-list/order-list.component';
import { ProductAmountListComponent } from './product-amount-list/product-amount-list.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatMenuModule} from '@angular/material/menu';
import {IdFilterPipe} from './pipes/id-filter.pipe';


@NgModule({
  declarations: [
    ProductCardComponent,
    ProductCardBoxComponent,
    ProductListDisplayComponent,
    PageTitleComponent,
    StandardDividerComponent,
    AddToCartDialogComponent,
    ConfirmWarnDialogComponent,
    WarningDialogComponent,
    PriceFilterPipe,
    StringFilterPipe,
    StringListFilterPipe,
    IdFilterPipe,
    OrderListComponent,
    ProductAmountListComponent
  ],
  imports: [
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTableModule,
    MatDialogModule,
    MatDividerModule,
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatExpansionModule,
    MatMenuModule
  ],
  exports: [
    ProductCardComponent,
    ProductCardBoxComponent,
    StandardDividerComponent,
    PageTitleComponent,
    ProductListDisplayComponent,
    PriceFilterPipe,
    StringFilterPipe,
    StringListFilterPipe,
    ProductAmountListComponent,
    OrderListComponent,
    IdFilterPipe
  ]
})
export class SharedModule { }
