import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesOrderItem, getSalesOrderItemIdentifier } from '../sales-order-item.model';

export type EntityResponseType = HttpResponse<ISalesOrderItem>;
export type EntityArrayResponseType = HttpResponse<ISalesOrderItem[]>;

@Injectable({ providedIn: 'root' })
export class SalesOrderItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-order-items', 'orderservice');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(salesOrderItem: ISalesOrderItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrderItem);
    return this.http
      .post<ISalesOrderItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(salesOrderItem: ISalesOrderItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrderItem);
    return this.http
      .put<ISalesOrderItem>(`${this.resourceUrl}/${getSalesOrderItemIdentifier(salesOrderItem) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(salesOrderItem: ISalesOrderItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrderItem);
    return this.http
      .patch<ISalesOrderItem>(`${this.resourceUrl}/${getSalesOrderItemIdentifier(salesOrderItem) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISalesOrderItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISalesOrderItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSalesOrderItemToCollectionIfMissing(
    salesOrderItemCollection: ISalesOrderItem[],
    ...salesOrderItemsToCheck: (ISalesOrderItem | null | undefined)[]
  ): ISalesOrderItem[] {
    const salesOrderItems: ISalesOrderItem[] = salesOrderItemsToCheck.filter(isPresent);
    if (salesOrderItems.length > 0) {
      const salesOrderItemCollectionIdentifiers = salesOrderItemCollection.map(
        salesOrderItemItem => getSalesOrderItemIdentifier(salesOrderItemItem)!
      );
      const salesOrderItemsToAdd = salesOrderItems.filter(salesOrderItemItem => {
        const salesOrderItemIdentifier = getSalesOrderItemIdentifier(salesOrderItemItem);
        if (salesOrderItemIdentifier == null || salesOrderItemCollectionIdentifiers.includes(salesOrderItemIdentifier)) {
          return false;
        }
        salesOrderItemCollectionIdentifiers.push(salesOrderItemIdentifier);
        return true;
      });
      return [...salesOrderItemsToAdd, ...salesOrderItemCollection];
    }
    return salesOrderItemCollection;
  }

  protected convertDateFromClient(salesOrderItem: ISalesOrderItem): ISalesOrderItem {
    return Object.assign({}, salesOrderItem, {
      shipped: salesOrderItem.shipped?.isValid() ? salesOrderItem.shipped.format(DATE_FORMAT) : undefined,
      delivered: salesOrderItem.delivered?.isValid() ? salesOrderItem.delivered.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.shipped = res.body.shipped ? dayjs(res.body.shipped) : undefined;
      res.body.delivered = res.body.delivered ? dayjs(res.body.delivered) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((salesOrderItem: ISalesOrderItem) => {
        salesOrderItem.shipped = salesOrderItem.shipped ? dayjs(salesOrderItem.shipped) : undefined;
        salesOrderItem.delivered = salesOrderItem.delivered ? dayjs(salesOrderItem.delivered) : undefined;
      });
    }
    return res;
  }
}
