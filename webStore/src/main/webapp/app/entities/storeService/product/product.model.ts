import { IPhoto } from 'app/entities/storeService/photo/photo.model';
import { UnitOfMeasurement } from 'app/entities/enumerations/unit-of-measurement.model';
import { ProductStatus } from 'app/entities/enumerations/product-status.model';

export interface IProduct {
  id?: number;
  name?: string;
  sku?: string;
  description?: string | null;
  srp?: number | null;
  taxable?: boolean | null;
  salesUnit?: UnitOfMeasurement | null;
  salesQuantity?: number | null;
  status?: ProductStatus | null;
  grosWeight?: number | null;
  netWeight?: number | null;
  length?: number | null;
  width?: number | null;
  height?: number | null;
  photos?: IPhoto[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public sku?: string,
    public description?: string | null,
    public srp?: number | null,
    public taxable?: boolean | null,
    public salesUnit?: UnitOfMeasurement | null,
    public salesQuantity?: number | null,
    public status?: ProductStatus | null,
    public grosWeight?: number | null,
    public netWeight?: number | null,
    public length?: number | null,
    public width?: number | null,
    public height?: number | null,
    public photos?: IPhoto[] | null
  ) {
    this.taxable = this.taxable ?? false;
  }
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
