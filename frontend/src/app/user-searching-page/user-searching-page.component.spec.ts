import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSearchingPageComponent } from './user-searching-page.component';

describe('UserSearchingPageComponent', () => {
  let component: UserSearchingPageComponent;
  let fixture: ComponentFixture<UserSearchingPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserSearchingPageComponent]
    });
    fixture = TestBed.createComponent(UserSearchingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
