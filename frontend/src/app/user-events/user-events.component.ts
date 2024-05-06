import { Component,  OnInit } from '@angular/core';
import { EventService } from '../event.service';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';



@Component({
  selector: 'app-user-events',
  templateUrl: './user-events.component.html',
  styleUrls: ['./user-events.component.scss']
})
export class UserEventsComponent implements OnInit {
  events: Event[] = [];

  constructor(private eventService: EventService, private router: Router) {}

  ngOnInit(): void {
    this.eventService.getUserEvents().subscribe(events => {
      this.events = events;
    });
  }


  editEvent(event: Event){
    this.eventService.setCurrentEvent(event);
    this.router.navigate(['/edit-event']);
  }

  addEvent(): void {
    this.router.navigate(['/add-event']);
  }
}