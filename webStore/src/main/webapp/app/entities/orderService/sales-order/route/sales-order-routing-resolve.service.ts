import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesOrder, SalesOrder } from '../sales-order.model';
import { SalesOrderService } from '../service/sales-order.service';

@Injectable({ providedIn: 'root' })
export class SalesOrderRoutingResolveService implements Resolve<ISalesOrder> {
  constructor(protected service: SalesOrderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalesOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((salesOrder: HttpResponse<SalesOrder>) => {
          if (salesOrder.body) {
            return of(salesOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalesOrder());
  }
}
