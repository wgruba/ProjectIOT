import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterSiteComponent } from './register-site.component';

describe('LoginSiteComponent', () => {
  let component: RegisterSiteComponent;
  let fixture: ComponentFixture<RegisterSiteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterSiteComponent]
    });
    fixture = TestBed.createComponent(RegisterSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
