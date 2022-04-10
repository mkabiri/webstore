import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SalesOrderItemComponent } from '../list/sales-order-item.component';
import { SalesOrderItemDetailComponent } from '../detail/sales-order-item-detail.component';
import { SalesOrderItemUpdateComponent } from '../update/sales-order-item-update.component';
import { SalesOrderItemRoutingResolveService } from './sales-order-item-routing-resolve.service';

const salesOrderItemRoute: Routes = [
  {
    path: '',
    component: SalesOrderItemComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalesOrderItemDetailComponent,
    resolve: {
      salesOrderItem: SalesOrderItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalesOrderItemUpdateComponent,
    resolve: {
      salesOrderItem: SalesOrderItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalesOrderItemUpdateComponent,
    resolve: {
      salesOrderItem: SalesOrderItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(salesOrderItemRoute)],
  exports: [RouterModule],
})
export class SalesOrderItemRoutingModule {}
