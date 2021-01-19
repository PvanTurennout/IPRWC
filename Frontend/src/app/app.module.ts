import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './main/header/header.component';
import { NavigationComponent } from './main/header/navigation.component';
import { FooterComponent } from './main/footer/footer.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ReactiveFormsModule } from '@angular/forms';
import { MatGridListModule } from '@angular/material/grid-list';
import { AuthenticationModule } from './authentication/authentication.module';
import { HomepageComponent } from './main/homepage/homepage.component';
import { SharedModule } from './shared/shared.module';
import { ProductsModule } from './products/products.module';
import { NotFoundComponent } from './main/not-found/not-found.component';
import { ShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { OrderModule } from './order/order.module';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthInterceptorService} from './typescript/interceptors/auth-interceptor-service';
import {MatMenuModule} from '@angular/material/menu';
import {StoreManagementModule} from './store-management/store-management.module';
import {MatBadgeModule} from '@angular/material/badge';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NavigationComponent,
    HomepageComponent,
    NotFoundComponent,
  ],
    imports: [
      MatSidenavModule,
      MatGridListModule,
      MatToolbarModule,
      MatListModule,
      MatIconModule,
      MatButtonModule,
      MatMenuModule,
      MatBadgeModule,

      BrowserModule,
      BrowserAnimationsModule,
      LayoutModule,
      ReactiveFormsModule,
      HttpClientModule,

      AuthenticationModule,
      SharedModule,
      ProductsModule,
      ShoppingCartModule,
      OrderModule,
      StoreManagementModule,
      AppRoutingModule
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
    ],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
