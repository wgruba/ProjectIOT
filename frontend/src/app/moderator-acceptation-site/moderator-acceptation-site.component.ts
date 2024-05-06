import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';
import { EventService } from '../event.service';
import { UserService } from '../user.service';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-moderator-acceptation-site',
  templateUrl: './moderator-acceptation-site.component.html',
  styleUrls: ['./moderator-acceptation-site.component.scss']
})
export class ModeratorAcceptationSiteComponent {
  isAdmin: boolean = false;
  events: Event[] = [];


  constructor(private router: Router, private eventService: EventService, public userService: UserService, public adminService: AdminService) {
  }

  ngOnInit(): void {
    const user = this.userService.getCurrentUser();

    this.adminService.getEventsToAcceptance().subscribe(data => {
      this.events = data;
    }, error => console.error(error));
  }

  applyFilters(){
    
  }

  showDetails(card: Event): void {
    this.eventService.setCurrentEvent(card);
    this.router.navigate(['/admin-event-details', card.id]);
  }
}
