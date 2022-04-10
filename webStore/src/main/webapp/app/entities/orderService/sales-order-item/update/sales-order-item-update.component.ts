import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISalesOrderItem, SalesOrderItem } from '../sales-order-item.model';
import { SalesOrderItemService } from '../service/sales-order-item.service';
import { ISalesOrder } from 'app/entities/orderService/sales-order/sales-order.model';
import { SalesOrderService } from 'app/entities/orderService/sales-order/service/sales-order.service';
import { SalesOrderItemStatus } from 'app/entities/enumerations/sales-order-item-status.model';

@Component({
  selector: 'jhi-sales-order-item-update',
  templateUrl: './sales-order-item-update.component.html',
})
export class SalesOrderItemUpdateComponent implements OnInit {
  isSaving = false;
  salesOrderItemStatusValues = Object.keys(SalesOrderItemStatus);

  salesOrdersSharedCollection: ISalesOrder[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    sku: [],
    taxable: [],
    grosWeight: [],
    shipped: [],
    delivered: [],
    status: [],
    quantity: [],
    unitPrice: [],
    amount: [],
    salesOrder: [],
  });

  constructor(
    protected salesOrderItemService: SalesOrderItemService,
    protected salesOrderService: SalesOrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesOrderItem }) => {
      this.updateForm(salesOrderItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesOrderItem = this.createFromForm();
    if (salesOrderItem.id !== undefined) {
      this.subscribeToSaveResponse(this.salesOrderItemService.update(salesOrderItem));
    } else {
      this.subscribeToSaveResponse(this.salesOrderItemService.create(salesOrderItem));
    }
  }

  trackSalesOrderById(_index: number, item: ISalesOrder): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesOrderItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(salesOrderItem: ISalesOrderItem): void {
    this.editForm.patchValue({
      id: salesOrderItem.id,
      name: salesOrderItem.name,
      sku: salesOrderItem.sku,
      taxable: salesOrderItem.taxable,
      grosWeight: salesOrderItem.grosWeight,
      shipped: salesOrderItem.shipped,
      delivered: salesOrderItem.delivered,
      status: salesOrderItem.status,
      quantity: salesOrderItem.quantity,
      unitPrice: salesOrderItem.unitPrice,
      amount: salesOrderItem.amount,
      salesOrder: salesOrderItem.salesOrder,
    });

    this.salesOrdersSharedCollection = this.salesOrderService.addSalesOrderToCollectionIfMissing(
      this.salesOrdersSharedCollection,
      salesOrderItem.salesOrder
    );
  }

  protected loadRelationshipsOptions(): void {
    this.salesOrderService
      .query()
      .pipe(map((res: HttpResponse<ISalesOrder[]>) => res.body ?? []))
      .pipe(
        map((salesOrders: ISalesOrder[]) =>
          this.salesOrderService.addSalesOrderToCollectionIfMissing(salesOrders, this.editForm.get('salesOrder')!.value)
        )
      )
      .subscribe((salesOrders: ISalesOrder[]) => (this.salesOrdersSharedCollection = salesOrders));
  }

  protected createFromForm(): ISalesOrderItem {
    return {
      ...new SalesOrderItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      sku: this.editForm.get(['sku'])!.value,
      taxable: this.editForm.get(['taxable'])!.value,
      grosWeight: this.editForm.get(['grosWeight'])!.value,
      shipped: this.editForm.get(['shipped'])!.value,
      delivered: this.editForm.get(['delivered'])!.value,
      status: this.editForm.get(['status'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      salesOrder: this.editForm.get(['salesOrder'])!.value,
    };
  }
}
