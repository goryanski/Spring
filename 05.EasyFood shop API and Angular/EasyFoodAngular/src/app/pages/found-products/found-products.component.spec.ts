import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FoundProductsComponent } from './found-products.component';

describe('FoundProductsComponent', () => {
  let component: FoundProductsComponent;
  let fixture: ComponentFixture<FoundProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FoundProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FoundProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
