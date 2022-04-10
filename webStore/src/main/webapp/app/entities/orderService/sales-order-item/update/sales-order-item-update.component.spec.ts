import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SalesOrderItemService } from '../service/sales-order-item.service';
import { ISalesOrderItem, SalesOrderItem } from '../sales-order-item.model';
import { ISalesOrder } from 'app/entities/orderService/sales-order/sales-order.model';
import { SalesOrderService } from 'app/entities/orderService/sales-order/service/sales-order.service';

import { SalesOrderItemUpdateComponent } from './sales-order-item-update.component';

describe('SalesOrderItem Management Update Component', () => {
  let comp: SalesOrderItemUpdateComponent;
  let fixture: ComponentFixture<SalesOrderItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesOrderItemService: SalesOrderItemService;
  let salesOrderService: SalesOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SalesOrderItemUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SalesOrderItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesOrderItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesOrderItemService = TestBed.inject(SalesOrderItemService);
    salesOrderService = TestBed.inject(SalesOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SalesOrder query and add missing value', () => {
      const salesOrderItem: ISalesOrderItem = { id: 456 };
      const salesOrder: ISalesOrder = { id: 70272 };
      salesOrderItem.salesOrder = salesOrder;

      const salesOrderCollection: ISalesOrder[] = [{ id: 76858 }];
      jest.spyOn(salesOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: salesOrderCollection })));
      const additionalSalesOrders = [salesOrder];
      const expectedCollection: ISalesOrder[] = [...additionalSalesOrders, ...salesOrderCollection];
      jest.spyOn(salesOrderService, 'addSalesOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ salesOrderItem });
      comp.ngOnInit();

      expect(salesOrderService.query).toHaveBeenCalled();
      expect(salesOrderService.addSalesOrderToCollectionIfMissing).toHaveBeenCalledWith(salesOrderCollection, ...additionalSalesOrders);
      expect(comp.salesOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const salesOrderItem: ISalesOrderItem = { id: 456 };
      const salesOrder: ISalesOrder = { id: 68045 };
      salesOrderItem.salesOrder = salesOrder;

      activatedRoute.data = of({ salesOrderItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(salesOrderItem));
      expect(comp.salesOrdersSharedCollection).toContain(salesOrder);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalesOrderItem>>();
      const salesOrderItem = { id: 123 };
      jest.spyOn(salesOrderItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesOrderItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesOrderItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesOrderItemService.update).toHaveBeenCalledWith(salesOrderItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalesOrderItem>>();
      const salesOrderItem = new SalesOrderItem();
      jest.spyOn(salesOrderItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesOrderItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesOrderItem }));
      saveSubject.complete();

      // THEN
      expect(salesOrderItemService.create).toHaveBeenCalledWith(salesOrderItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalesOrderItem>>();
      const salesOrderItem = { id: 123 };
      jest.spyOn(salesOrderItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesOrderItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesOrderItemService.update).toHaveBeenCalledWith(salesOrderItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSalesOrderById', () => {
      it('Should return tracked SalesOrder primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSalesOrderById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
