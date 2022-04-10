import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalesOrderItem } from '../sales-order-item.model';

@Component({
  selector: 'jhi-sales-order-item-detail',
  templateUrl: './sales-order-item-detail.component.html',
})
export class SalesOrderItemDetailComponent implements OnInit {
  salesOrderItem: ISalesOrderItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesOrderItem }) => {
      this.salesOrderItem = salesOrderItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
