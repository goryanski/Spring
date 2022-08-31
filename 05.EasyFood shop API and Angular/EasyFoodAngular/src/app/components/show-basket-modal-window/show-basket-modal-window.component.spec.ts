import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowBasketModalWindowComponent } from './show-basket-modal-window.component';

describe('ShowBasketModalWindowComponent', () => {
  let component: ShowBasketModalWindowComponent;
  let fixture: ComponentFixture<ShowBasketModalWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowBasketModalWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowBasketModalWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
