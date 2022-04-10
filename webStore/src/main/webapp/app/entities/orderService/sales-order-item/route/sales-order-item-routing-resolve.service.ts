import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesOrderItem, SalesOrderItem } from '../sales-order-item.model';
import { SalesOrderItemService } from '../service/sales-order-item.service';

@Injectable({ providedIn: 'root' })
export class SalesOrderItemRoutingResolveService implements Resolve<ISalesOrderItem> {
  constructor(protected service: SalesOrderItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalesOrderItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((salesOrderItem: HttpResponse<SalesOrderItem>) => {
          if (salesOrderItem.body) {
            return of(salesOrderItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalesOrderItem());
  }
}
