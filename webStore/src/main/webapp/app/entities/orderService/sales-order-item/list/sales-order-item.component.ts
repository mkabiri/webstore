import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesOrderItem } from '../sales-order-item.model';
import { SalesOrderItemService } from '../service/sales-order-item.service';
import { SalesOrderItemDeleteDialogComponent } from '../delete/sales-order-item-delete-dialog.component';

@Component({
  selector: 'jhi-sales-order-item',
  templateUrl: './sales-order-item.component.html',
})
export class SalesOrderItemComponent implements OnInit {
  salesOrderItems?: ISalesOrderItem[];
  isLoading = false;

  constructor(protected salesOrderItemService: SalesOrderItemService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.salesOrderItemService.query().subscribe({
      next: (res: HttpResponse<ISalesOrderItem[]>) => {
        this.isLoading = false;
        this.salesOrderItems = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISalesOrderItem): number {
    return item.id!;
  }

  delete(salesOrderItem: ISalesOrderItem): void {
    const modalRef = this.modalService.open(SalesOrderItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesOrderItem = salesOrderItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
