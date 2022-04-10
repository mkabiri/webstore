import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SalesOrderStatus } from 'app/entities/enumerations/sales-order-status.model';
import { ISalesOrder, SalesOrder } from '../sales-order.model';

import { SalesOrderService } from './sales-order.service';

describe('SalesOrder Service', () => {
  let service: SalesOrderService;
  let httpMock: HttpTestingController;
  let elemDefault: ISalesOrder;
  let expectedResult: ISalesOrder | ISalesOrder[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SalesOrderService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      salesOrderNumber: 'AAAAAAA',
      customerId: 'AAAAAAA',
      placed: currentDate,
      cancelled: currentDate,
      shipped: currentDate,
      completed: currentDate,
      status: SalesOrderStatus.PENDING,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          placed: currentDate.format(DATE_TIME_FORMAT),
          cancelled: currentDate.format(DATE_TIME_FORMAT),
          shipped: currentDate.format(DATE_TIME_FORMAT),
          completed: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SalesOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          placed: currentDate.format(DATE_TIME_FORMAT),
          cancelled: currentDate.format(DATE_TIME_FORMAT),
          shipped: currentDate.format(DATE_TIME_FORMAT),
          completed: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          placed: currentDate,
          cancelled: currentDate,
          shipped: currentDate,
          completed: currentDate,
        },
        returnedFromService
      );

      service.create(new SalesOrder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          salesOrderNumber: 'BBBBBB',
          customerId: 'BBBBBB',
          placed: currentDate.format(DATE_TIME_FORMAT),
          cancelled: currentDate.format(DATE_TIME_FORMAT),
          shipped: currentDate.format(DATE_TIME_FORMAT),
          completed: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          placed: currentDate,
          cancelled: currentDate,
          shipped: currentDate,
          completed: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesOrder', () => {
      const patchObject = Object.assign(
        {
          customerId: 'BBBBBB',
          placed: currentDate.format(DATE_TIME_FORMAT),
          cancelled: currentDate.format(DATE_TIME_FORMAT),
          shipped: currentDate.format(DATE_TIME_FORMAT),
          completed: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
        },
        new SalesOrder()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          placed: currentDate,
          cancelled: currentDate,
          shipped: currentDate,
          completed: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          salesOrderNumber: 'BBBBBB',
          customerId: 'BBBBBB',
          placed: currentDate.format(DATE_TIME_FORMAT),
          cancelled: currentDate.format(DATE_TIME_FORMAT),
          shipped: currentDate.format(DATE_TIME_FORMAT),
          completed: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          placed: currentDate,
          cancelled: currentDate,
          shipped: currentDate,
          completed: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SalesOrder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSalesOrderToCollectionIfMissing', () => {
      it('should add a SalesOrder to an empty array', () => {
        const salesOrder: ISalesOrder = { id: 123 };
        expectedResult = service.addSalesOrderToCollectionIfMissing([], salesOrder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesOrder);
      });

      it('should not add a SalesOrder to an array that contains it', () => {
        const salesOrder: ISalesOrder = { id: 123 };
        const salesOrderCollection: ISalesOrder[] = [
          {
            ...salesOrder,
          },
          { id: 456 },
        ];
        expectedResult = service.addSalesOrderToCollectionIfMissing(salesOrderCollection, salesOrder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesOrder to an array that doesn't contain it", () => {
        const salesOrder: ISalesOrder = { id: 123 };
        const salesOrderCollection: ISalesOrder[] = [{ id: 456 }];
        expectedResult = service.addSalesOrderToCollectionIfMissing(salesOrderCollection, salesOrder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesOrder);
      });

      it('should add only unique SalesOrder to an array', () => {
        const salesOrderArray: ISalesOrder[] = [{ id: 123 }, { id: 456 }, { id: 96825 }];
        const salesOrderCollection: ISalesOrder[] = [{ id: 123 }];
        expectedResult = service.addSalesOrderToCollectionIfMissing(salesOrderCollection, ...salesOrderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesOrder: ISalesOrder = { id: 123 };
        const salesOrder2: ISalesOrder = { id: 456 };
        expectedResult = service.addSalesOrderToCollectionIfMissing([], salesOrder, salesOrder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesOrder);
        expect(expectedResult).toContain(salesOrder2);
      });

      it('should accept null and undefined values', () => {
        const salesOrder: ISalesOrder = { id: 123 };
        expectedResult = service.addSalesOrderToCollectionIfMissing([], null, salesOrder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesOrder);
      });

      it('should return initial array if no SalesOrder is added', () => {
        const salesOrderCollection: ISalesOrder[] = [{ id: 123 }];
        expectedResult = service.addSalesOrderToCollectionIfMissing(salesOrderCollection, undefined, null);
        expect(expectedResult).toEqual(salesOrderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
