import {Pipe, PipeTransform} from '@angular/core';
import {Product} from '../../typescript/model/product.model';

@Pipe({
  name: 'stringListFilter'
})
export class StringListFilterPipe implements PipeTransform{
  transform(value: Product[], selector: string, selection: string[]): Product[] {
    if (this.isValueNull(selection)){
      return value;
    }

    const productList = [];

    for (let product of value) {
      for (let filter of selection) {
        if (product[selector] === filter) {
          productList.push(product);
        }
      }
    }

    return productList;
  }

  isValueNull(x: string[]){
    return x === undefined || x.length === 0 ;
  }

}
