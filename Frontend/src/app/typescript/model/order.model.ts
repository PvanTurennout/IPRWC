import {ShoppingCartItem} from './shoppingCartItem.model';
import {OrderStatus} from '../enums/order-status.enum';
import {Product, ProductDTO} from './product.model';

export interface OrderResponse{
  orderId: number;
  orderStatus: number;
  items: {product: ProductDTO, amount: number}[];
  firstName: string;
  lastName: string;
  address: string;
  zipcode: string;
  town: string;
  shippingMethod: string;
  totalCost: number;
  orderPlacementDate: Date;
}

export interface OrderRequest{
  orderId?: number;
  orderStatus?: number;
  items: {productId: number, amount: number}[];
  firstName: string;
  lastName: string;
  address: string;
  zipCode: string;
  town: string;
  shippingMethod: string;
  totalPrice: number;
}

export class Order {
  private _orderId: number;
  private _orderStatus: OrderStatus;
  private _items: ShoppingCartItem[];
  private _firstName: string;
  private _lastName: string;
  private _address: string;
  private _zipcode: string;
  private _town: string;
  private _shippingMethod: string;
  private _totalCost: number;
  private _orderDate: Date;

  constructor(items: ShoppingCartItem[], firstName: string, lastName: string, address: string,
              zipcode: string, town: string, shippingMethod: string, totalCost: number, orderId?: number, orderStatus?: OrderStatus, orderDate?: Date) {
    this._orderStatus = orderStatus;
    this._items = items;
    this._firstName = firstName;
    this._lastName = lastName;
    this._address = address;
    this._zipcode = zipcode;
    this._town = town;
    this._shippingMethod = shippingMethod;
    this._totalCost = totalCost;
    this._orderId = orderId;
    this._orderDate = orderDate;
  }

  static fromResponse(response: OrderResponse): Order{
    return new Order(
      Order.itemsFromRequest(response.items),
      response.firstName,
      response.lastName,
      response.address,
      response.zipcode,
      response.town,
      response.shippingMethod,
      response.totalCost,
      response.orderId,
      response.orderStatus,
      new Date(response.orderPlacementDate)
    );
  }

  private static itemsFromRequest(items: {product: ProductDTO, amount: number}[]){
    const products = [];
    for (let item of items){
      products.push(new ShoppingCartItem(Product.fromRequest(item.product), item.amount));
    }
    return products;
  }

  get orderId(): number {
    return this._orderId;
  }

  set orderId(value: number) {
    this._orderId = value;
  }

  get orderStatus(): OrderStatus {
    return this._orderStatus;
  }

  set orderStatus(value: OrderStatus) {
    this._orderStatus = value;
  }

  get items(): ShoppingCartItem[] {
    return this._items;
  }

  set items(value: ShoppingCartItem[]) {
    this._items = value;
  }

  get firstName(): string {
    return this._firstName;
  }

  set firstName(value: string) {
    this._firstName = value;
  }

  get lastName(): string {
    return this._lastName;
  }

  set lastName(value: string) {
    this._lastName = value;
  }

  get address(): string {
    return this._address;
  }

  set address(value: string) {
    this._address = value;
  }

  get zipcode(): string {
    return this._zipcode;
  }

  set zipcode(value: string) {
    this._zipcode = value;
  }

  get town(): string {
    return this._town;
  }

  set town(value: string) {
    this._town = value;
  }

  get shippingMethod(): string {
    return this._shippingMethod;
  }

  set shippingMethod(value: string) {
    this._shippingMethod = value;
  }

  get totalCost(): number {
    return this._totalCost;
  }

  set totalCost(value: number) {
    this._totalCost = value;
  }

  get orderPlacementDate(): Date{
    return this._orderDate;
  }

  set orderPlacementDate(date: Date) {
    this._orderDate = date;
  }

  toCreateRequest(): OrderRequest{
    return {
      items: this.itemsToRequest(),
      firstName: this._firstName,
      lastName: this._lastName,
      address: this._address,
      zipCode: this._zipcode,
      town: this._town,
      shippingMethod: this._shippingMethod,
      totalPrice: this._totalCost
    };
  }

  toUpdateRequest(): OrderRequest{
    return {
      orderId: this._orderId,
      orderStatus: this._orderStatus,
      items: this.itemsToRequest(),
      firstName: this._firstName,
      lastName: this._lastName,
      address: this._address,
      zipCode: this._zipcode,
      town: this._town,
      shippingMethod: this._shippingMethod,
      totalPrice: this._totalCost
    };
  }

  private itemsToRequest() {
    const requestArray = [];
    for (let item of this._items) {
      requestArray.push({productId: item.product.productId, amount: item.amount});
    }
    return requestArray;
  }
}
