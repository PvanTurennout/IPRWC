import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductAmountListComponent } from './product-amount-list.component';

describe('ProductAmountListComponent', () => {
  let component: ProductAmountListComponent;
  let fixture: ComponentFixture<ProductAmountListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductAmountListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductAmountListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
