import { Pipe, PipeTransform } from '@angular/core';
import {Product} from '../../typescript/model/product.model';

@Pipe({
  name: 'priceFilter'
})
export class PriceFilterPipe implements PipeTransform {

  transform(value: Product[], min: number, max: number): Product[] {
    if (this.isValueNull(min) && this.isValueNull(max)){
      return value;
    }
    const list = [];

    if (this.isValueNull(min)){
      for (let product of value){
        if (product.price <= max){
          list.push(product);
        }
      }
    }

    if (this.isValueNull(max)){
      for (let product of value){
        if (product.price >= min){
          list.push(product);
        }
      }
    }

    for (let product of value){
      if (product.price >= min && product.price <= max){
        list.push(product);
      }
    }

    return list;
  }

  isValueNull(x: number){
    return x === undefined || x === 0 || x === null;
  }

}
