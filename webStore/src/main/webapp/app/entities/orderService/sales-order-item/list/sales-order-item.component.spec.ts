import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SalesOrderItemService } from '../service/sales-order-item.service';

import { SalesOrderItemComponent } from './sales-order-item.component';

describe('SalesOrderItem Management Component', () => {
  let comp: SalesOrderItemComponent;
  let fixture: ComponentFixture<SalesOrderItemComponent>;
  let service: SalesOrderItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SalesOrderItemComponent],
    })
      .overrideTemplate(SalesOrderItemComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesOrderItemComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalesOrderItemService);

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
    expect(comp.salesOrderItems?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
