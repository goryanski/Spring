import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderedProductsModalWindowComponent } from './ordered-products-modal-window.component';

describe('OrderedProductsModalWindowComponent', () => {
  let component: OrderedProductsModalWindowComponent;
  let fixture: ComponentFixture<OrderedProductsModalWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderedProductsModalWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderedProductsModalWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
