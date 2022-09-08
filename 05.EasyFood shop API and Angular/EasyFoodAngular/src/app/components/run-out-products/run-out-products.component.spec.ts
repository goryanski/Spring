import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RunOutProductsComponent } from './run-out-products.component';

describe('RunOutProductsComponent', () => {
  let component: RunOutProductsComponent;
  let fixture: ComponentFixture<RunOutProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RunOutProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RunOutProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
