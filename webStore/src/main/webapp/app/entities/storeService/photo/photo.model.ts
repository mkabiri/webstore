import { IProduct } from 'app/entities/storeService/product/product.model';

export interface IPhoto {
  id?: number;
  photoContentType?: string | null;
  photo?: string | null;
  product?: IProduct | null;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public photoContentType?: string | null,
    public photo?: string | null,
    public product?: IProduct | null
  ) {}
}

export function getPhotoIdentifier(photo: IPhoto): number | undefined {
  return photo.id;
}
