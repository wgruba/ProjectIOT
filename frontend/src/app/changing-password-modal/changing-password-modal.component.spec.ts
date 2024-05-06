import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangingPasswordModalComponent } from './changing-password-modal.component';

describe('ChangingPasswordModalComponent', () => {
  let component: ChangingPasswordModalComponent;
  let fixture: ComponentFixture<ChangingPasswordModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChangingPasswordModalComponent]
    });
    fixture = TestBed.createComponent(ChangingPasswordModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
