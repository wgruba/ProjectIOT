import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddingCategoriesModalComponent } from './adding-categories-modal.component';

describe('AddingCategoriesModalComponent', () => {
  let component: AddingCategoriesModalComponent;
  let fixture: ComponentFixture<AddingCategoriesModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddingCategoriesModalComponent]
    });
    fixture = TestBed.createComponent(AddingCategoriesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
