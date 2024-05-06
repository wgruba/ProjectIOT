import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EventService } from '../event.service';
import { UserService } from '../user.service';
import { FilterSearchService } from '../filter-search.service';
import { Event } from '../models/event.model';

@Component({
  selector: 'app-user-searching-page',
  templateUrl: './user-searching-page.component.html',
  styleUrls: ['./user-searching-page.component.scss']
})
export class UserSearchingPageComponent implements OnInit {
  roleClass: string = '';
  isAdmin: boolean = false;
  isUser: boolean = false;
  filterForm: FormGroup;
  events: Event[] = [];

  constructor(
    private router: Router,
    private eventService: EventService,
    public userService: UserService,
    private formBuilder: FormBuilder,
    private filterSearchService: FilterSearchService
  ) {
    this.filterForm = this.formBuilder.group({
      historyczne: false,
      rezerwacja: "0",
      koszt: "0",
      wiek: "0",
      miejscaMin: null,
      miejscaMax: null
    });
  }

  ngOnInit(): void {
    const filter = localStorage.getItem('filter');
    if (filter != null) {
      this.filterForm.patchValue(JSON.parse(filter));
    }

    const user = this.userService.getCurrentUser();
    if (user) {
      this.isAdmin = user.permissionLevel === 'MODERATOR' || user.permissionLevel === "ADMIN";
      this.isUser = !this.isAdmin;
      this.roleClass = user.permissionLevel === 'VERIFIED_USER' ? 'admin-header' : 'user-header';
    }

    // Subskrypcja na zmiany eventów z FilterSearchService
    this.filterSearchService.filteredEvents$.subscribe(events => {
      this.events = events;
    });
  }

  applyFilters(): void {
    console.log(this.filterForm.value);
    localStorage.setItem('filter', JSON.stringify(this.filterForm.value));
  
    // Pobierz aktualne parametry filtrów z serwisu
    const currentFilters = this.filterSearchService.getCurrentFilterParameters();
    
    const filteredEventParameters = {
      name: currentFilters?.name ?? '',
      categoryList: currentFilters?.categoryList ?? [],
      localisation: currentFilters?.localisation ?? '',
      startDate: currentFilters?.startDate ?? '',
      endDate: currentFilters?.endDate ?? '',
      isFinished: this.filterForm.value.historyczne,
      reservation: this.filterForm.value.rezerwacja,
      isFree: this.filterForm.value.koszt,
      ageGroup: this.filterForm.value.wiek
    };
  
    this.filterSearchService.getFilteredEvents(filteredEventParameters).subscribe(response => {
      this.filterSearchService.updateFilteredEvents(response);
    });
    this.filterSearchService.updateFilterParameters(filteredEventParameters);
  }

  showDetails(card: Event): void {
    this.eventService.setCurrentEvent(card);
    this.router.navigate(['/event-details', card.id]);
  }

  resetFilters(): void {
    this.filterForm.reset({
      historyczne: false,
      rezerwacja: 0,
      koszt: 0,
      wiek: "0",
      miejscaMin: null,
      miejscaMax: null
    });
    this.filterSearchService.resetFilterParameters();
  }
}
