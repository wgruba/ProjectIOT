import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { EventService } from '../event.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../user.service';
import { Event } from '../models/event.model';
import { Category } from '../models/Category.model';
import { CategoryService } from '../category.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-event-site',
  templateUrl: './add-event-site.component.html',
  styleUrls: ['./add-event-site.component.scss']
})
export class AddEventSiteComponent {
  eventForm !: FormGroup;
  categories! : Category[];
  selectedCategoryIds: number[] = [];
  selectedSubcategoryIds: number[] = [];
  id: number = 0;



  constructor(private eventService: EventService, private snackBar: MatSnackBar, public userService: UserService, public categoryService: CategoryService, private router: Router){}

  ngOnInit(): void {
    this.eventForm = new FormGroup({
      eventName: new FormControl('', Validators.required),
      participants: new FormControl(50, [Validators.required, Validators.min(1)]),
      isFree: new FormControl(false),
      isOnline: new FormControl(false),
      isReservationRequired: new FormControl(false),
      ageRequirement: new FormControl('FAMILY_FRIENDLY'),
      startTime: new FormControl('', [Validators.required, this.validateStartTime]),
      endTime: new FormControl('', Validators.required),
      description: new FormControl(''),
      localisation: new FormControl(''),
      imageUrl: new FormControl('') 
    }, { validators: this.validateDates });
    this.loadCategories();

    this.eventService.getLastID().subscribe(response => {
      this.id = response + 1
    });

    this.categoryService.getCategoriesFromDatabase().subscribe(response => {
      this.categories = response;
    });
  }

  validateStartTime(control: FormControl): {[key: string]: any} | null {
    const startTime = new Date(control.value);
    if (startTime < new Date()) {
      return { 'startTimeInvalid': 'Start time cannot be in the past.' };
    }
    return null;
  }

  validateDates(control: AbstractControl): {[key: string]: any} | null {
    const group = control as FormGroup;
    const start = group.get('startTime')?.value;
    const end = group.get('endTime')?.value;
    if (start && end && new Date(start) > new Date(end)) {
      return { 'dateInvalid': 'End time cannot be earlier than start time.' };
    }
    return null;
  }

  toggleCategorySelection(categoryId: number): void {
    const index = this.selectedCategoryIds.indexOf(categoryId);
    if (index > -1) {
      this.selectedCategoryIds.splice(index, 1);
    } else {
      this.selectedCategoryIds.push(categoryId);
    }
  }

  toggleSubcategorySelection(subcategoryId: number): void {
    const index = this.selectedSubcategoryIds.indexOf(subcategoryId);
    if (index > -1) {
      this.selectedSubcategoryIds.splice(index, 1);
    } else {
      this.selectedSubcategoryIds.push(subcategoryId);
    }
  }


  getSelectedCategoryNames(): string[] {
    return this.selectedCategoryIds.map(categoryId => 
      this.categories.find(category => category.id === categoryId)?.name || ''
    );
  }
  
  getSelectedSubcategoryNames(): string[] {
    return this.selectedSubcategoryIds.map(subcategoryId => {
      for (const category of this.categories) {
        const subcategory = category.subcategories.find(sub => sub.first === subcategoryId);
        if (subcategory) {
          return subcategory.second;
        }
      }
      return '';
    });
  }


  getCategoryByName(name: string): Category | undefined {
    return this.categories.find(category => category.name === name);
  }
  
  getSubcategoryByName(name: string): { categoryId: number, subcategoryId: number } | undefined {
    for (const category of this.categories) {
      const subcategory = category.subcategories.find(sub => sub.second === name);
      if (subcategory) {
        return { categoryId: category.id, subcategoryId: subcategory.first };
      }
    }
    return undefined;
  }
  
  removeSelectedCategory(categoryName: string): void {
    const category = this.getCategoryByName(categoryName);
    if (category) {
      this.selectedCategoryIds = this.selectedCategoryIds.filter(id => id !== category.id);
      // Update UI or backend as necessary
    }
  }
  
  removeSelectedSubcategory(subcategoryName: string): void {
    const subcategoryInfo = this.getSubcategoryByName(subcategoryName);
    if (subcategoryInfo) {
      this.selectedSubcategoryIds = this.selectedSubcategoryIds.filter(id => id !== subcategoryInfo.subcategoryId);
      // Update UI or backend as necessary
    }
  }

  removeCategory(categoryId: number): void {
    this.selectedCategoryIds = this.selectedCategoryIds.filter(id => id !== categoryId);
    this.snackBar.open('Kategoria usunięta', 'Zamknij', { duration: 3000 });
  }
  
  removeSubcategory(subcategoryId: number): void {
    this.selectedSubcategoryIds = this.selectedSubcategoryIds.filter(id => id !== subcategoryId);
    this.snackBar.open('Podkategoria usunięta', 'Zamknij', { duration: 3000 });
  }
  

  
  loadCategories() {
    // Load categories from your backend and assign to this.categories
  }



  prepareEventData(formValue: any): Event {
    let allCategoryIds: number[] = [...this.selectedCategoryIds, ...this.selectedSubcategoryIds];

    return {
        id: this.id,
        name: formValue.eventName,
        organizer: this.userService.getCurrentUser().id,
        categoryList: allCategoryIds,
        clientList: [],
        description: formValue.description,
        size: formValue.participants,
        localisation: formValue.localisation,
        isFree: formValue.isFree,
        isReservationNecessary: formValue.isReservationRequired,
        ageGroup: formValue.ageRequirement,
        startDate: formValue.startTime,
        endDate: formValue.endTime,
        eventStatus: 'TO_ACCEPTANCE',
        imageUrl: formValue.imageUrl,
    };
  }

  onSubmit(): void {
      if (this.eventForm.valid) {
        const eventData = this.prepareEventData(this.eventForm.value);
        console.log(eventData);
        this.eventService.addEvent(eventData).subscribe(
          response => {
            console.log('Event added:', response);
            this.snackBar.open("Wydarzenie zostało dodane", 'Zamknij', {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
              this.router.navigate(['/user-events']);
          },
          error => {
            console.error('Error adding event:', error);
            this.snackBar.open("Coś poszło nie tak, Spróbuj ponownie", 'Zamknij', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
          }
        );
      }
  }
}
