import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEventSiteComponent } from './add-event-site.component';

describe('AddEventSiteComponent', () => {
  let component: AddEventSiteComponent;
  let fixture: ComponentFixture<AddEventSiteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddEventSiteComponent]
    });
    fixture = TestBed.createComponent(AddEventSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
