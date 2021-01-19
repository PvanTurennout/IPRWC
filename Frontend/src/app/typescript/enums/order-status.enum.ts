export enum OrderStatus{
  received,
  processed,
  send,
  delivered,
  archived
}


export function getOrderStatusString(status: OrderStatus){
  switch (status){
    case OrderStatus.received:
      return 'received';
    case OrderStatus.processed:
      return 'processed';
    case OrderStatus.send:
      return 'send';
    case OrderStatus.delivered:
      return 'delivered';
    case OrderStatus.archived:
      return 'archived';
    default:
      return '';
  }
}
