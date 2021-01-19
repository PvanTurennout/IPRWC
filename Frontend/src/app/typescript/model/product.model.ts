import {Watch, WatchDTO} from './watch.model';

export interface ProductDTO {
  productId?: number;
  price: number;
  imagePath: string;
  name: string;
  description: string;
  stock: number;
  brand: string;
  watch?: WatchDTO;
}

export class Product {
  private readonly _productId: number;
  private readonly _name: string;
  private readonly _brand: string;
  private readonly _price: number;
  private readonly _description: string;
  private readonly _stock: number;
  private readonly _imagePath: string;
  private _watch: Watch;


  constructor(productId: number, name: string, brand: string, price: number,
              description: string, stock: number, imagePath: string, watch?: Watch) {
    this._productId = productId;
    this._name = name;
    this._brand = brand;
    this._price = price;
    this._description = description;
    this._stock = stock;
    this._imagePath = imagePath;
    this._watch = watch;
  }

  static fromRequest(request: ProductDTO): Product{
    if (request.watch){
      return new Product(request.productId, request.name, request.brand, request.price,
        request.description, request.stock, request.imagePath, Watch.fromRequest(request.watch));
    } else {
      return new Product(request.productId, request.name, request.brand, request.price,
        request.description, request.stock, request.imagePath);
    }
  }

  get productId(): number {
    return this._productId;
  }

  get name(): string {
    return this._name;
  }

  get brand(): string {
    return this._brand;
  }

  get price(): number {
    return this._price;
  }

  get stock(): number {
    return this._stock;
  }

  get imagePath(): string {
    return this._imagePath;
  }

  get description(): string {
    return this._description;
  }

  get watch(): Watch{
    return this._watch;
  }

  set watch(watch: Watch){
    this._watch = watch;
  }

  toUpdateRequest(): ProductDTO {
    return {
      productId: this._productId,
      brand: this._brand,
      name: this._name,
      description: this._description,
      price: this._price,
      imagePath: this._imagePath,
      stock: this._stock,
      watch: this._watch.toUpdateRequest()
    };
  }

  toCreateRequest(): ProductDTO {
    return {
      brand: this._brand,
      name: this._name,
      description: this._description,
      price: this._price,
      imagePath: this._imagePath,
      stock: this._stock,
      watch: this._watch.toCreateRequest()
    };
  }
}
