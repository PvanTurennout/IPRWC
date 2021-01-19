import {Product} from './product.model';
import {CantBeNullError} from '../exceptions/cantbenull.exception';

export class ShoppingCartItem {

  private readonly _product: Product;
  private _amount: number;
  private _productSubtotal: number;

  constructor(product: Product, amount?: number, subTotal?: number) {
    this._product = product;

    this._amount = !amount ? 1 : amount;

    if (subTotal){
      this._productSubtotal = subTotal;
    } else {
      this.calcProductSubtotal();
    }
  }

  private calcProductSubtotal() {
    this._productSubtotal = this._amount * this._product.price;
  }

  public upAmount() {
    this._amount++;
    this.calcProductSubtotal();
  }

  public lowerAmount() {
    if (this._amount === 1) {
      throw new CantBeNullError(`Product ${this._product.name} cannot be at amount 0, please delete instead`);
    }

    this._amount--;
    this.calcProductSubtotal();
  }

  get amount(): number {
    return this._amount;
  }

  get productSubtotal(): number {
    return this._productSubtotal;
  }

  get product(): Product {
    return this._product;
  }

}
