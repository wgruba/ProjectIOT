import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditingEventModalComponent } from './editing-event-modal.component';

describe('EditingEventModalComponent', () => {
  let component: EditingEventModalComponent;
  let fixture: ComponentFixture<EditingEventModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditingEventModalComponent]
    });
    fixture = TestBed.createComponent(EditingEventModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
