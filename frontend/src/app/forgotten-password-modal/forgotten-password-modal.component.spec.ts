import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgottenPasswordModalComponent } from './forgotten-password-modal.component';

describe('ForgottenPasswordModalComponent', () => {
  let component: ForgottenPasswordModalComponent;
  let fixture: ComponentFixture<ForgottenPasswordModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ForgottenPasswordModalComponent]
    });
    fixture = TestBed.createComponent(ForgottenPasswordModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
