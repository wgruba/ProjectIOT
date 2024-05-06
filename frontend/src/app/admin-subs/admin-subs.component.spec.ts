import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSubsComponent } from './admin-subs.component';

describe('AdminSubsComponent', () => {
  let component: AdminSubsComponent;
  let fixture: ComponentFixture<AdminSubsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSubsComponent]
    });
    fixture = TestBed.createComponent(AdminSubsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
