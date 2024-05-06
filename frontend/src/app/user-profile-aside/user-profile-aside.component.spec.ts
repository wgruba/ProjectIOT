import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileAsideComponent } from './user-profile-aside.component';

describe('UserProfileAsideComponent', () => {
  let component: UserProfileAsideComponent;
  let fixture: ComponentFixture<UserProfileAsideComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserProfileAsideComponent]
    });
    fixture = TestBed.createComponent(UserProfileAsideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
