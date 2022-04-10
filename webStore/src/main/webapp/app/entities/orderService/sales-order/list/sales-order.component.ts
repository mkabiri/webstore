import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesOrder } from '../sales-order.model';
import { SalesOrderService } from '../service/sales-order.service';
import { SalesOrderDeleteDialogComponent } from '../delete/sales-order-delete-dialog.component';

@Component({
  selector: 'jhi-sales-order',
  templateUrl: './sales-order.component.html',
})
export class SalesOrderComponent implements OnInit {
  salesOrders?: ISalesOrder[];
  isLoading = false;

  constructor(protected salesOrderService: SalesOrderService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.salesOrderService.query().subscribe({
      next: (res: HttpResponse<ISalesOrder[]>) => {
        this.isLoading = false;
        this.salesOrders = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISalesOrder): number {
    return item.id!;
  }

  delete(salesOrder: ISalesOrder): void {
    const modalRef = this.modalService.open(SalesOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesOrder = salesOrder;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
