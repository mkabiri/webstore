import dayjs from 'dayjs/esm';
import { ISalesOrder } from 'app/entities/orderService/sales-order/sales-order.model';
import { SalesOrderItemStatus } from 'app/entities/enumerations/sales-order-item-status.model';

export interface ISalesOrderItem {
  id?: number;
  name?: string | null;
  sku?: string | null;
  taxable?: boolean | null;
  grosWeight?: number | null;
  shipped?: dayjs.Dayjs | null;
  delivered?: dayjs.Dayjs | null;
  status?: SalesOrderItemStatus | null;
  quantity?: number | null;
  unitPrice?: number | null;
  amount?: number | null;
  salesOrder?: ISalesOrder | null;
}

export class SalesOrderItem implements ISalesOrderItem {
  constructor(
    public id?: number,
    public name?: string | null,
    public sku?: string | null,
    public taxable?: boolean | null,
    public grosWeight?: number | null,
    public shipped?: dayjs.Dayjs | null,
    public delivered?: dayjs.Dayjs | null,
    public status?: SalesOrderItemStatus | null,
    public quantity?: number | null,
    public unitPrice?: number | null,
    public amount?: number | null,
    public salesOrder?: ISalesOrder | null
  ) {
    this.taxable = this.taxable ?? false;
  }
}

export function getSalesOrderItemIdentifier(salesOrderItem: ISalesOrderItem): number | undefined {
  return salesOrderItem.id;
}
