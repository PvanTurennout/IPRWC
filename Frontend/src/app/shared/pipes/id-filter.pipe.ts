import {Pipe, PipeTransform} from '@angular/core';
import {Order} from '../../typescript/model/order.model';

@Pipe({name: 'idFilter'})
export class IdFilterPipe implements PipeTransform{
  transform(value: Order[], search: string): Order[] {
    if (this.isValueNull(search)){
      return value;
    }

    const list = [];
    for (let order of value) {
      if (order.orderId.toLocaleString().match(search)){
        list.push(order);
      }
    }

    return list;
  }

  isValueNull(x: string){
    return x === undefined || x === '' || x === null;
  }

}
