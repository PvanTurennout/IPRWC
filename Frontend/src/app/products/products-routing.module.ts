import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProductComponent} from './product/product.component';
import {ProductPageComponent} from './product-page/product-page.component';
import {WishlistComponent} from './wishlist/wishlist.component';
import {AuthGuard} from '../typescript/guards/auth.guard';
import {ProductResolverService} from './product-page/product-resolver.service';


const routes: Routes = [
  {path: 'watches', component: ProductComponent},
  {path: 'product/:id', component: ProductPageComponent, resolve: {productResolver: ProductResolverService} },
  {path: 'wishlist', canActivate: [AuthGuard] , component: WishlistComponent}
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
