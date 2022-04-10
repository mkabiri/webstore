import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SalesOrderService } from '../service/sales-order.service';

import { SalesOrderComponent } from './sales-order.component';

describe('SalesOrder Management Component', () => {
  let comp: SalesOrderComponent;
  let fixture: ComponentFixture<SalesOrderComponent>;
  let service: SalesOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SalesOrderComponent],
    })
      .overrideTemplate(SalesOrderComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesOrderComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalesOrderService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.salesOrders?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
