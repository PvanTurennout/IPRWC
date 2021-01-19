import {Injectable} from '@angular/core';
import {ShoppingCartItem} from '../typescript/model/shoppingCartItem.model';
import {Product} from '../typescript/model/product.model';
import {BehaviorSubject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class ShoppingCartService{
  private readonly LOCALSTORAGE_KEY = 'ShoppingCart';
  private shoppingCart: ShoppingCartItem[] = [];
  public shoppingCartSubject = new BehaviorSubject<ShoppingCartItem[]>([]);
  public totalCost = new BehaviorSubject<number>(0);

  // Cart Management
  public add(product: Product, amount?: number){

    for (let item of this.shoppingCart){
      if (item.product.productId === product.productId){
        item.upAmount();
        this.updateSubjects();
        return;
      }
    }

    this.shoppingCart.push(new ShoppingCartItem(product, amount));
    this.updateSubjects();
  }

  public remove(index: number){
    this.shoppingCart.splice(index, 1);
    this.updateSubjects();
  }

  public empty(){
    this.shoppingCart = [];
    this.updateSubjects();
  }

  public upAmount(index: number){
    this.shoppingCart[index].upAmount();
    this.updateSubjects();
  }

  public lowerAmount(index: number) {
    this.shoppingCart[index].lowerAmount();
    this.updateSubjects();
  }

  public isEmpty(){
    return !(this.shoppingCart.length > 0);
  }

  // Subject Management
  private updateSubjects(){
    this.updateShoppingCart();
    this.updateTotalCost();
    this.store();
  }

  private updateTotalCost(){
    let cost = 0;
    this.shoppingCart.forEach(item => cost += item.productSubtotal);
    this.totalCost.next(cost);
  }

  private updateShoppingCart(){
    this.shoppingCartSubject.next(this.shoppingCart.slice());
  }

  // Local Storage Management
  public store(){
    localStorage.setItem(this.LOCALSTORAGE_KEY, JSON.stringify(this.shoppingCart));
    // localStorage.setItem(this.LOCALSTORAGE_KEY, JSON.stringify(this.makeStoreObject(this.shoppingCart[0])));
  }

  public retrieve(){
    const lsCart = JSON.parse(localStorage.getItem(this.LOCALSTORAGE_KEY));

    if (lsCart){
      for (let item of lsCart){
        this.shoppingCart.push(this.parseStoredObject(item));
      }
      this.updateSubjects();
    }
  }

  parseStoredObject(
    object: {
      _product: {
        _brand: string,
        _description: string,
        _imagePath: string,
        _name: string,
        _price: number,
        _productId: number,
        _stock: number
      },
      _amount: number,
      _productSubtotal: number})
  {
    return new ShoppingCartItem(
      new Product(
        object._product._productId,
        object._product._name,
        object._product._brand,
        object._product._price,
        object._product._description,
        object._product._stock,
        object._product._imagePath
      ),
      object._amount,
      object._productSubtotal
    );
  }

}
