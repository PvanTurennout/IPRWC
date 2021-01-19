import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Product} from '../../typescript/model/product.model';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {ProductService} from '../product.service';

@Injectable({providedIn: 'root'})
export class ProductResolverService implements Resolve<Product>{

  constructor(private productService: ProductService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> | Promise<Product> | Product {
    const prodId = route.params.id;

    return this.productService.getFullProduct(prodId);
  }

}
