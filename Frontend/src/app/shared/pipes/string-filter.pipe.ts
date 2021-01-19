import {Pipe, PipeTransform} from '@angular/core';
import {Product} from '../../typescript/model/product.model';

@Pipe({
  name: 'stringFilter'
})
export class StringFilterPipe implements PipeTransform{
  transform(value: Product[], search: string): Product[] {
    if (this.isValueNull(search)){
      return value;
    }

    const productList = [];

    for (let product of value) {
      if (product.name.toLocaleLowerCase().match(search.toLocaleLowerCase())){
        productList.push(product);
      }
    }

    return productList;
  }

  isValueNull(x: string){
    return x === undefined || x === '' || x === null;
  }

}
