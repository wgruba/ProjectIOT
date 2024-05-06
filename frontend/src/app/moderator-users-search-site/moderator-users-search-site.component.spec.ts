import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorUsersSearchSiteComponent } from './moderator-users-search-site.component';

describe('ModeratorUsersSearchSiteComponent', () => {
  let component: ModeratorUsersSearchSiteComponent;
  let fixture: ComponentFixture<ModeratorUsersSearchSiteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeratorUsersSearchSiteComponent]
    });
    fixture = TestBed.createComponent(ModeratorUsersSearchSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
