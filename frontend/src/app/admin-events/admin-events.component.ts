import { Component } from '@angular/core';
import { EventService } from '../event.service';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import { User } from '../models/user.model';

@Component({
  selector: 'app-admin-events',
  templateUrl: './admin-events.component.html',
  styleUrls: ['./admin-events.component.scss']
})
export class AdminEventsComponent {
  events: Event[] = [];
  user!: User;

  constructor(public eventService: EventService, private router: Router, private route: ActivatedRoute, public userService: UserService) {}

  ngOnInit(): void {
    const userName = this.route.snapshot.params['name'];
    this.userService.getUserFromDatabaseByName(userName).subscribe(data => {
      this.user = data;
      this.eventService.getUserEventsByName(data.id).subscribe(events => {
        this.events = events;
      });
    }, error => console.error(error));
    
  }


  editEvent(event: Event){
    this.eventService.setCurrentEvent(event);
    this.router.navigate(['/edit-event']);
  }

  
}
