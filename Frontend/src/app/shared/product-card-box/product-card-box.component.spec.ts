import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCardBoxComponent } from './product-card-box.component';

describe('ProductCardBoxComponent', () => {
  let component: ProductCardBoxComponent;
  let fixture: ComponentFixture<ProductCardBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductCardBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductCardBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
