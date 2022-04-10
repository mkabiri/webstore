import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SalesOrderItemDetailComponent } from './sales-order-item-detail.component';

describe('SalesOrderItem Management Detail Component', () => {
  let comp: SalesOrderItemDetailComponent;
  let fixture: ComponentFixture<SalesOrderItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalesOrderItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ salesOrderItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SalesOrderItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalesOrderItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesOrderItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.salesOrderItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
