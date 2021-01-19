import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ShoppingCartService} from '../../shopping-cart/shopping-cart.service';


@Component({
  selector: 'app-payment-creditcard',
  styles: [``],
  template: `
    <div class="absoluteCenter">
      <h1>{{method}}</h1>
      <p>amount pay: {{amount | currency: 'EUR'}}</p>
      <button mat-raised-button  routerLink="/">Pay (Back To Home)</button>
    </div>
  `
})
export class PaymentPageComponent implements OnInit{
  method: string;
  amount: number;

  constructor(private route: ActivatedRoute, private shoppingCartService: ShoppingCartService) {
  }

  ngOnInit() {
    this.method = this.route.snapshot.data.method;
    this.amount = this.shoppingCartService.totalCost.value;

    this.shoppingCartService.empty();
  }
}
