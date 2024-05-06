import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { FilteredEventParameters } from './models/FilteredEventParameters.model';
import { Event } from './models/event.model';

@Injectable({
  providedIn: 'root'
})
export class FilterSearchService {
  private filteredEventsSubject = new BehaviorSubject<Event[]>([]);
  private filterParametersSubject = new BehaviorSubject<FilteredEventParameters | null>(null);

  filteredEvents$ = this.filteredEventsSubject.asObservable();
  filterParameters$ = this.filterParametersSubject.asObservable();

  constructor(private http: HttpClient) {}

  getFilteredEvents(filteredEventParameters: FilteredEventParameters): Observable<Event[]> {
    let baseUrl = `http://localhost:8080`;
    return this.http.post<Event[]>(`${baseUrl}/unauthorized/events/filter`, filteredEventParameters);
  }

  getCurrentFilterParameters(): FilteredEventParameters | null {
    return this.filterParametersSubject.getValue();
  }

  updateFilteredEvents(events: Event[]): void {
    this.filteredEventsSubject.next(events);
  }

  updateFilterParameters(parameters: FilteredEventParameters): void {
    this.filterParametersSubject.next(parameters);
  }

  resetFilterParameters(): void {
    const defaultFilters: FilteredEventParameters = {
      name: '',
      categoryList: [],
      localisation: '',
      startDate: '',
      endDate: '',
      isFinished: false,
      reservation: 0,
      isFree: 0,
      ageGroup: 'OVER16'
    };
    this.updateFilterParameters(defaultFilters);
  }
}
