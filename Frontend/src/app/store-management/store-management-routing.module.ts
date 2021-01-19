import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {StoreManagementComponent} from './store-management/store-management.component';
import {EditProductComponent} from './edit-product/edit-product.component';
import {ProductResolverService} from '../products/product-page/product-resolver.service';
import {SellerGuard} from '../typescript/guards/seller.guard';

const routes: Routes = [
  {path: 'store', canActivate: [SellerGuard], component: StoreManagementComponent},
  {path: 'new/product', canActivate: [SellerGuard], component: EditProductComponent},
  {path: 'edit/product/:id', canActivate: [SellerGuard], component: EditProductComponent, resolve: {productResolver: ProductResolverService}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StoreManagementRoutingModule { }
