import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowProductModalWindowComponent } from './show-product-modal-window.component';

describe('ShowProductModalWindowComponent', () => {
  let component: ShowProductModalWindowComponent;
  let fixture: ComponentFixture<ShowProductModalWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowProductModalWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowProductModalWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
