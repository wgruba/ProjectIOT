import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorAcceptationSiteComponent } from './moderator-acceptation-site.component';

describe('ModeratorAcceptationSiteComponent', () => {
  let component: ModeratorAcceptationSiteComponent;
  let fixture: ComponentFixture<ModeratorAcceptationSiteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeratorAcceptationSiteComponent]
    });
    fixture = TestBed.createComponent(ModeratorAcceptationSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
