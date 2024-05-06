import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeratorAcceptationDetailsSiteComponent } from './moderator-acceptation-details-site.component';

describe('ModeratorAcceptationDetailsSiteComponent', () => {
  let component: ModeratorAcceptationDetailsSiteComponent;
  let fixture: ComponentFixture<ModeratorAcceptationDetailsSiteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeratorAcceptationDetailsSiteComponent]
    });
    fixture = TestBed.createComponent(ModeratorAcceptationDetailsSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
