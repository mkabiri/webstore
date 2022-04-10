import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SalesOrderComponent } from './list/sales-order.component';
import { SalesOrderDetailComponent } from './detail/sales-order-detail.component';
import { SalesOrderUpdateComponent } from './update/sales-order-update.component';
import { SalesOrderDeleteDialogComponent } from './delete/sales-order-delete-dialog.component';
import { SalesOrderRoutingModule } from './route/sales-order-routing.module';

@NgModule({
  imports: [SharedModule, SalesOrderRoutingModule],
  declarations: [SalesOrderComponent, SalesOrderDetailComponent, SalesOrderUpdateComponent, SalesOrderDeleteDialogComponent],
  entryComponents: [SalesOrderDeleteDialogComponent],
})
export class OrderServiceSalesOrderModule {}
