import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesOrderItem } from '../sales-order-item.model';
import { SalesOrderItemService } from '../service/sales-order-item.service';

@Component({
  templateUrl: './sales-order-item-delete-dialog.component.html',
})
export class SalesOrderItemDeleteDialogComponent {
  salesOrderItem?: ISalesOrderItem;

  constructor(protected salesOrderItemService: SalesOrderItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesOrderItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
