import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { SalesOrderItemStatus } from 'app/entities/enumerations/sales-order-item-status.model';
import { ISalesOrderItem, SalesOrderItem } from '../sales-order-item.model';

import { SalesOrderItemService } from './sales-order-item.service';

describe('SalesOrderItem Service', () => {
  let service: SalesOrderItemService;
  let httpMock: HttpTestingController;
  let elemDefault: ISalesOrderItem;
  let expectedResult: ISalesOrderItem | ISalesOrderItem[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SalesOrderItemService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      sku: 'AAAAAAA',
      taxable: false,
      grosWeight: 0,
      shipped: currentDate,
      delivered: currentDate,
      status: SalesOrderItemStatus.PENDING,
      quantity: 0,
      unitPrice: 0,
      amount: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          shipped: currentDate.format(DATE_FORMAT),
          delivered: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SalesOrderItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          shipped: currentDate.format(DATE_FORMAT),
          delivered: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          shipped: currentDate,
          delivered: currentDate,
        },
        returnedFromService
      );

      service.create(new SalesOrderItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesOrderItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          sku: 'BBBBBB',
          taxable: true,
          grosWeight: 1,
          shipped: currentDate.format(DATE_FORMAT),
          delivered: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          quantity: 1,
          unitPrice: 1,
          amount: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          shipped: currentDate,
          delivered: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesOrderItem', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          taxable: true,
          grosWeight: 1,
          shipped: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          quantity: 1,
          amount: 1,
        },
        new SalesOrderItem()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          shipped: currentDate,
          delivered: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesOrderItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          sku: 'BBBBBB',
          taxable: true,
          grosWeight: 1,
          shipped: currentDate.format(DATE_FORMAT),
          delivered: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          quantity: 1,
          unitPrice: 1,
          amount: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          shipped: currentDate,
          delivered: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SalesOrderItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSalesOrderItemToCollectionIfMissing', () => {
      it('should add a SalesOrderItem to an empty array', () => {
        const salesOrderItem: ISalesOrderItem = { id: 123 };
        expectedResult = service.addSalesOrderItemToCollectionIfMissing([], salesOrderItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesOrderItem);
      });

      it('should not add a SalesOrderItem to an array that contains it', () => {
        const salesOrderItem: ISalesOrderItem = { id: 123 };
        const salesOrderItemCollection: ISalesOrderItem[] = [
          {
            ...salesOrderItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addSalesOrderItemToCollectionIfMissing(salesOrderItemCollection, salesOrderItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesOrderItem to an array that doesn't contain it", () => {
        const salesOrderItem: ISalesOrderItem = { id: 123 };
        const salesOrderItemCollection: ISalesOrderItem[] = [{ id: 456 }];
        expectedResult = service.addSalesOrderItemToCollectionIfMissing(salesOrderItemCollection, salesOrderItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesOrderItem);
      });

      it('should add only unique SalesOrderItem to an array', () => {
        const salesOrderItemArray: ISalesOrderItem[] = [{ id: 123 }, { id: 456 }, { id: 35345 }];
        const salesOrderItemCollection: ISalesOrderItem[] = [{ id: 123 }];
        expectedResult = service.addSalesOrderItemToCollectionIfMissing(salesOrderItemCollection, ...salesOrderItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesOrderItem: ISalesOrderItem = { id: 123 };
        const salesOrderItem2: ISalesOrderItem = { id: 456 };
        expectedResult = service.addSalesOrderItemToCollectionIfMissing([], salesOrderItem, salesOrderItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesOrderItem);
        expect(expectedResult).toContain(salesOrderItem2);
      });

      it('should accept null and undefined values', () => {
        const salesOrderItem: ISalesOrderItem = { id: 123 };
        expectedResult = service.addSalesOrderItemToCollectionIfMissing([], null, salesOrderItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesOrderItem);
      });

      it('should return initial array if no SalesOrderItem is added', () => {
        const salesOrderItemCollection: ISalesOrderItem[] = [{ id: 123 }];
        expectedResult = service.addSalesOrderItemToCollectionIfMissing(salesOrderItemCollection, undefined, null);
        expect(expectedResult).toEqual(salesOrderItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
