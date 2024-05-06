import { Component, OnInit } from '@angular/core';
import { EventService } from '../event.service';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../models/event.model';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmationDialogComponentComponent } from '../confirmation-dialog-component/confirmation-dialog-component.component';
import { MatDialog } from '@angular/material/dialog';
import { Category } from '../models/Category.model';
import { CategoryService } from '../category.service';




@Component({
  selector: 'app-description-page',
  templateUrl: './description-page.component.html',
  styleUrls: ['./description-page.component.scss']
})
export class DescriptionPageComponent implements OnInit{
  event !: Event;
  latitude!: number;
  longitude!: number;
  isSubscribed = false;
  categories! : Category[];
  organizerName: Organizer = {name: ""};
  isLogged = false;

  constructor(
    private route: ActivatedRoute, 
    private eventService: EventService,
    private userService: UserService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    public categoryService: CategoryService
  ) {

  }

  ngOnInit(): void {
    const eventId = this.route.snapshot.params['id'];
    this.event = this.eventService.getCurrentEvent();
    const currentUser = this.userService.getCurrentUser();
    if (currentUser.permissionLevel != 'None'){
      this.isLogged = true;
      if (Array.isArray(currentUser.subscribedEvents)) {
        this.isSubscribed = currentUser.subscribedEvents.includes(this.event.id);
      } else {
        this.isSubscribed = false;
      }
    }
    this.getOrganizerUsername();
    this.categoryService.getCategoriesFromDatabase().subscribe(response => {
      this.categories = response;
    });
  }

  getGoogleCalendarUrl(event: Event): string {
    const startTime = this.formatDateToGoogleCalendar(new Date(event.startDate)); //need to adjust later with proper data
    const endTime = this.formatDateToGoogleCalendar(new Date(event.endDate));
  
    const details = encodeURIComponent(event.description);
    const location = encodeURIComponent(event.name);
    const title = encodeURIComponent(event.name);
  
    return `https://calendar.google.com/calendar/r/eventedit?text=${title}&dates=${startTime}/${endTime}&details=${details}&location=${location}`;
  }

  getOrganizerUsername() {
    this.eventService.getOrganizerName(this.event.organizer).subscribe(data => {
      this.organizerName.name = data
    }, error => console.error(error));
  }

  formatDateToGoogleCalendar(date: Date): string {
    return date.toISOString().replace(/-|:|\.\d\d\d/g, '');
  }

  addToGoogleCalendar(event: Event): void {
    const url = this.getGoogleCalendarUrl(event);
    window.open(url, '_blank');
  }

  subscribeEvent(){
    const currentEventId = this.eventService.getCurrentEvent().id;
    this.userService.subscribeEvent(currentEventId).subscribe(response => {
      this.isSubscribed = true;
      const user = this.userService.getCurrentUser();
      user.subscribedEvents.push(currentEventId)
      this.userService.setCurrentUser(user)
    });
  }

  getCategoriesNames(): string[] {
    for (let category of this.event.categoryList){
    }
    return [];
  }

  getSelectedCategoryNames(): string[] {
    if (!this.event || !this.event.categoryList || !this.categories) {
      return []; // Return an empty array if the data is not yet loaded
    }
  
    return this.event.categoryList.map(categoryId => 
      this.categories.find(category => category.id === categoryId)?.name || ''
    );
  }
  
  getSelectedSubcategoryNames(): string[] {
    if (!this.event || !this.event.categoryList || !this.categories) {
      return []; // Return an empty array if the data is not yet loaded
    }
  
    return this.event.categoryList.map(subcategoryId => {
      for (const category of this.categories) {
        const subcategory = category.subcategories?.find(sub => sub.first === subcategoryId);
        if (subcategory) {
          return subcategory.second;
        }
      }
      return '';
    });
  }
  
  

  getNames(): string[]{
    return [...this.getSelectedCategoryNames(), ...this.getSelectedSubcategoryNames()] 
  }

  unsubscribeEvent(eventId: number){
    this.userService.unsubscribeEvent(eventId).subscribe(response => {
      this.snackBar.open("Subskrybcja zotała usunięta", 'Zamknij', {
        duration: 4000,
        horizontalPosition: 'right',
        verticalPosition: 'top', });
      });
      const user = this.userService.getCurrentUser();
      user.subscribedEvents = user.subscribedEvents.filter(subscribedEventId => subscribedEventId !== eventId);
      this.userService.setCurrentUser(user);
      this.isSubscribed = false;
  }

  openUnsubscribeEventModal(eventId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponentComponent, {
      width: '350px',
      data: {title: 'Potwierdzenie', message: 'Czy na pewno chcesz anulować subskrybcję tego wydarzenia?'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.unsubscribeEvent(eventId);
        window.location.reload();
      }
    });
  }
}

export class Organizer {
  constructor(
    public name: string) { }
}
