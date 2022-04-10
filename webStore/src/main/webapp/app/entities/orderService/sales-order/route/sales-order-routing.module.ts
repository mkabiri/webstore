import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SalesOrderComponent } from '../list/sales-order.component';
import { SalesOrderDetailComponent } from '../detail/sales-order-detail.component';
import { SalesOrderUpdateComponent } from '../update/sales-order-update.component';
import { SalesOrderRoutingResolveService } from './sales-order-routing-resolve.service';

const salesOrderRoute: Routes = [
  {
    path: '',
    component: SalesOrderComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalesOrderDetailComponent,
    resolve: {
      salesOrder: SalesOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalesOrderUpdateComponent,
    resolve: {
      salesOrder: SalesOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalesOrderUpdateComponent,
    resolve: {
      salesOrder: SalesOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(salesOrderRoute)],
  exports: [RouterModule],
})
export class SalesOrderRoutingModule {}
