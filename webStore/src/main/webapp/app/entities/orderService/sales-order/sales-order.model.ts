import dayjs from 'dayjs/esm';
import { ISalesOrderItem } from 'app/entities/orderService/sales-order-item/sales-order-item.model';
import { SalesOrderStatus } from 'app/entities/enumerations/sales-order-status.model';

export interface ISalesOrder {
  id?: number;
  salesOrderNumber?: string | null;
  customerId?: string | null;
  placed?: dayjs.Dayjs | null;
  cancelled?: dayjs.Dayjs | null;
  shipped?: dayjs.Dayjs | null;
  completed?: dayjs.Dayjs | null;
  status?: SalesOrderStatus | null;
  orderItems?: ISalesOrderItem[] | null;
}

export class SalesOrder implements ISalesOrder {
  constructor(
    public id?: number,
    public salesOrderNumber?: string | null,
    public customerId?: string | null,
    public placed?: dayjs.Dayjs | null,
    public cancelled?: dayjs.Dayjs | null,
    public shipped?: dayjs.Dayjs | null,
    public completed?: dayjs.Dayjs | null,
    public status?: SalesOrderStatus | null,
    public orderItems?: ISalesOrderItem[] | null
  ) {}
}

export function getSalesOrderIdentifier(salesOrder: ISalesOrder): number | undefined {
  return salesOrder.id;
}
