import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISalesOrder, SalesOrder } from '../sales-order.model';
import { SalesOrderService } from '../service/sales-order.service';
import { SalesOrderStatus } from 'app/entities/enumerations/sales-order-status.model';

@Component({
  selector: 'jhi-sales-order-update',
  templateUrl: './sales-order-update.component.html',
})
export class SalesOrderUpdateComponent implements OnInit {
  isSaving = false;
  salesOrderStatusValues = Object.keys(SalesOrderStatus);

  editForm = this.fb.group({
    id: [],
    salesOrderNumber: [],
    customerId: [],
    placed: [],
    cancelled: [],
    shipped: [],
    completed: [],
    status: [],
  });

  constructor(protected salesOrderService: SalesOrderService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesOrder }) => {
      if (salesOrder.id === undefined) {
        const today = dayjs().startOf('day');
        salesOrder.placed = today;
        salesOrder.cancelled = today;
        salesOrder.shipped = today;
        salesOrder.completed = today;
      }

      this.updateForm(salesOrder);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesOrder = this.createFromForm();
    if (salesOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.salesOrderService.update(salesOrder));
    } else {
      this.subscribeToSaveResponse(this.salesOrderService.create(salesOrder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesOrder>>): void {
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

  protected updateForm(salesOrder: ISalesOrder): void {
    this.editForm.patchValue({
      id: salesOrder.id,
      salesOrderNumber: salesOrder.salesOrderNumber,
      customerId: salesOrder.customerId,
      placed: salesOrder.placed ? salesOrder.placed.format(DATE_TIME_FORMAT) : null,
      cancelled: salesOrder.cancelled ? salesOrder.cancelled.format(DATE_TIME_FORMAT) : null,
      shipped: salesOrder.shipped ? salesOrder.shipped.format(DATE_TIME_FORMAT) : null,
      completed: salesOrder.completed ? salesOrder.completed.format(DATE_TIME_FORMAT) : null,
      status: salesOrder.status,
    });
  }

  protected createFromForm(): ISalesOrder {
    return {
      ...new SalesOrder(),
      id: this.editForm.get(['id'])!.value,
      salesOrderNumber: this.editForm.get(['salesOrderNumber'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      placed: this.editForm.get(['placed'])!.value ? dayjs(this.editForm.get(['placed'])!.value, DATE_TIME_FORMAT) : undefined,
      cancelled: this.editForm.get(['cancelled'])!.value ? dayjs(this.editForm.get(['cancelled'])!.value, DATE_TIME_FORMAT) : undefined,
      shipped: this.editForm.get(['shipped'])!.value ? dayjs(this.editForm.get(['shipped'])!.value, DATE_TIME_FORMAT) : undefined,
      completed: this.editForm.get(['completed'])!.value ? dayjs(this.editForm.get(['completed'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
