import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SalesOrderItemComponent } from './list/sales-order-item.component';
import { SalesOrderItemDetailComponent } from './detail/sales-order-item-detail.component';
import { SalesOrderItemUpdateComponent } from './update/sales-order-item-update.component';
import { SalesOrderItemDeleteDialogComponent } from './delete/sales-order-item-delete-dialog.component';
import { SalesOrderItemRoutingModule } from './route/sales-order-item-routing.module';

@NgModule({
  imports: [SharedModule, SalesOrderItemRoutingModule],
  declarations: [
    SalesOrderItemComponent,
    SalesOrderItemDetailComponent,
    SalesOrderItemUpdateComponent,
    SalesOrderItemDeleteDialogComponent,
  ],
  entryComponents: [SalesOrderItemDeleteDialogComponent],
})
export class OrderServiceSalesOrderItemModule {}
