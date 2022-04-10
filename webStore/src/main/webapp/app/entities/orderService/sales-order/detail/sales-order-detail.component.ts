import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalesOrder } from '../sales-order.model';

@Component({
  selector: 'jhi-sales-order-detail',
  templateUrl: './sales-order-detail.component.html',
})
export class SalesOrderDetailComponent implements OnInit {
  salesOrder: ISalesOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesOrder }) => {
      this.salesOrder = salesOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
