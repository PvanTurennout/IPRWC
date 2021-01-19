import {Group} from '../enums/group.enum';

export class User{
  private _token: string;
  private readonly _refreshToken: string;
  private _expirationDate: Date;
  private _group: Group;
  private _userId: number;
  private _wishList: number[];


  constructor(refreshToken: string, token?: string, expireDate?: number, group?: Group, wishlist?: number[], id?: number) {
    this._token = token;
    this._refreshToken = refreshToken;
    this._expirationDate = new Date(expireDate * 1000);
    this._wishList = wishlist;
    this._group = group;
    this._userId = id;
  }


  get token(): string {
    return this._token;
  }

  set token(token: string) {
    this._token = token;
  }

  get refreshToken(): string {
    return this._refreshToken;
  }

  set expirationDate(expDate: number) {
    this._expirationDate = new Date(expDate * 1000);
  }

  get wishlist() {
    return this._wishList;
  }

  set wishlist(value:number[]) {
    this._wishList = value;
  }

  get userId() {
    return this._userId;
  }

  set userId(value: number){
    this._userId = value;
  }

  set group(value: Group){
    this._group = value;
  }

  isInGroup(group: Group){
    return this._group === group;
  }

  getExpirationTime() {
    return this._expirationDate.getTime() - new Date().getTime();
  }

  isProductWished(productId: number): boolean {
    return this._wishList.includes(+productId);
  }
}
