import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesOrder, getSalesOrderIdentifier } from '../sales-order.model';

export type EntityResponseType = HttpResponse<ISalesOrder>;
export type EntityArrayResponseType = HttpResponse<ISalesOrder[]>;

@Injectable({ providedIn: 'root' })
export class SalesOrderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-orders', 'orderservice');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(salesOrder: ISalesOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrder);
    return this.http
      .post<ISalesOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(salesOrder: ISalesOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrder);
    return this.http
      .put<ISalesOrder>(`${this.resourceUrl}/${getSalesOrderIdentifier(salesOrder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(salesOrder: ISalesOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrder);
    return this.http
      .patch<ISalesOrder>(`${this.resourceUrl}/${getSalesOrderIdentifier(salesOrder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISalesOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISalesOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSalesOrderToCollectionIfMissing(
    salesOrderCollection: ISalesOrder[],
    ...salesOrdersToCheck: (ISalesOrder | null | undefined)[]
  ): ISalesOrder[] {
    const salesOrders: ISalesOrder[] = salesOrdersToCheck.filter(isPresent);
    if (salesOrders.length > 0) {
      const salesOrderCollectionIdentifiers = salesOrderCollection.map(salesOrderItem => getSalesOrderIdentifier(salesOrderItem)!);
      const salesOrdersToAdd = salesOrders.filter(salesOrderItem => {
        const salesOrderIdentifier = getSalesOrderIdentifier(salesOrderItem);
        if (salesOrderIdentifier == null || salesOrderCollectionIdentifiers.includes(salesOrderIdentifier)) {
          return false;
        }
        salesOrderCollectionIdentifiers.push(salesOrderIdentifier);
        return true;
      });
      return [...salesOrdersToAdd, ...salesOrderCollection];
    }
    return salesOrderCollection;
  }

  protected convertDateFromClient(salesOrder: ISalesOrder): ISalesOrder {
    return Object.assign({}, salesOrder, {
      placed: salesOrder.placed?.isValid() ? salesOrder.placed.toJSON() : undefined,
      cancelled: salesOrder.cancelled?.isValid() ? salesOrder.cancelled.toJSON() : undefined,
      shipped: salesOrder.shipped?.isValid() ? salesOrder.shipped.toJSON() : undefined,
      completed: salesOrder.completed?.isValid() ? salesOrder.completed.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.placed = res.body.placed ? dayjs(res.body.placed) : undefined;
      res.body.cancelled = res.body.cancelled ? dayjs(res.body.cancelled) : undefined;
      res.body.shipped = res.body.shipped ? dayjs(res.body.shipped) : undefined;
      res.body.completed = res.body.completed ? dayjs(res.body.completed) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((salesOrder: ISalesOrder) => {
        salesOrder.placed = salesOrder.placed ? dayjs(salesOrder.placed) : undefined;
        salesOrder.cancelled = salesOrder.cancelled ? dayjs(salesOrder.cancelled) : undefined;
        salesOrder.shipped = salesOrder.shipped ? dayjs(salesOrder.shipped) : undefined;
        salesOrder.completed = salesOrder.completed ? dayjs(salesOrder.completed) : undefined;
      });
    }
    return res;
  }
}
