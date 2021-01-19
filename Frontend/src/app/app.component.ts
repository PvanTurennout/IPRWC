import {Component, OnDestroy, OnInit} from '@angular/core';
import {ShoppingCartService} from './shopping-cart/shopping-cart.service';
import {AuthenticationService} from './authentication/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy{
  title = 'WatchCom';

  constructor(private shoppingCartService: ShoppingCartService, private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.shoppingCartService.retrieve();
    this.authService.autoLogin();
  }

  ngOnDestroy() {
    this.shoppingCartService.store();
  }
}
