import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SalesOrderDetailComponent } from './sales-order-detail.component';

describe('SalesOrder Management Detail Component', () => {
  let comp: SalesOrderDetailComponent;
  let fixture: ComponentFixture<SalesOrderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalesOrderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ salesOrder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SalesOrderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalesOrderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesOrder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.salesOrder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
