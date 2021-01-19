import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Product, ProductDTO} from '../typescript/model/product.model';
import {catchError, map} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {handleHttpErrors} from '../typescript/utils/http-error-handeling';

@Injectable({providedIn: 'root'})
export class ProductService{

  static APIENDPOINT = environment.uriPrefix + 'product';

  constructor(private http: HttpClient) {
  }

  getAllProducts(){
    return this.getProductList('');
  }

  getHighlightedProducts(){
    return this.getProductList('/highlighted-products');
  }

  getBestSellingProducts(){
    return this.getProductList('/top-selling-products');
  }

  private getProductList(uri: string) {
    return this.http.get<ProductDTO[]>(ProductService.APIENDPOINT + uri)
      .pipe(
        catchError( err => throwError(handleHttpErrors(err)) ),
        map(response => this.mapProductList(response))
      );
  }

  private mapProductList(response: ProductDTO[]){
    const productList: Product[] = [];

    response.map((item: ProductDTO) => productList.push(Product.fromRequest(item)));

    return productList;
  }

  getFullProduct(productId: number){
    return this.http.get<ProductDTO>(ProductService.APIENDPOINT + '/' + productId)
      .pipe(
        catchError(err => throwError(handleHttpErrors(err)) ),
        map( response => {
          return Product.fromRequest(response);
        })
      );
  }

  getSpecifiedProductList(productIds: number[]){
    return this.http.post<ProductDTO[]>(ProductService.APIENDPOINT + '/list', productIds)
      .pipe(
        catchError( err => throwError(handleHttpErrors(err)) ),
        map(response => this.mapProductList(response))
      );
  }

  uploadImage(image: File) {
    const body = new FormData();
    body.append('productImage', image);
    body.append('productImageFileName', image.name);
    return this.http.post<{imageFileName: string;}>(environment.uriPrefix + 'image', body).pipe(
      catchError(err => throwError(handleHttpErrors(err)))
    );
  }

  deleteProduct(productId: number) {
    return this.http.delete(ProductService.APIENDPOINT + '/' + productId)
      .pipe(
        catchError(err => throwError(handleHttpErrors(err)))
      );
  }

  updateProduct(product: Product){
    return this.http.put(ProductService.APIENDPOINT, product.toUpdateRequest())
      .pipe(
        catchError(err => throwError(handleHttpErrors(err)))
      );
  }

  createProduct(product: Product){
    return this.http.post(ProductService.APIENDPOINT, product.toCreateRequest())
      .pipe(
        catchError(err => throwError(handleHttpErrors(err)))
      );
  }
}
