import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SalesOrderService } from '../service/sales-order.service';
import { ISalesOrder, SalesOrder } from '../sales-order.model';

import { SalesOrderUpdateComponent } from './sales-order-update.component';

describe('SalesOrder Management Update Component', () => {
  let comp: SalesOrderUpdateComponent;
  let fixture: ComponentFixture<SalesOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesOrderService: SalesOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SalesOrderUpdateComponent],
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
      .overrideTemplate(SalesOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesOrderService = TestBed.inject(SalesOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesOrder: ISalesOrder = { id: 456 };

      activatedRoute.data = of({ salesOrder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(salesOrder));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalesOrder>>();
      const salesOrder = { id: 123 };
      jest.spyOn(salesOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesOrder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesOrderService.update).toHaveBeenCalledWith(salesOrder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalesOrder>>();
      const salesOrder = new SalesOrder();
      jest.spyOn(salesOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesOrder }));
      saveSubject.complete();

      // THEN
      expect(salesOrderService.create).toHaveBeenCalledWith(salesOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SalesOrder>>();
      const salesOrder = { id: 123 };
      jest.spyOn(salesOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesOrderService.update).toHaveBeenCalledWith(salesOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
