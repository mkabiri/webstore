import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesOrder } from '../sales-order.model';
import { SalesOrderService } from '../service/sales-order.service';

@Component({
  templateUrl: './sales-order-delete-dialog.component.html',
})
export class SalesOrderDeleteDialogComponent {
  salesOrder?: ISalesOrder;

  constructor(protected salesOrderService: SalesOrderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesOrderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
