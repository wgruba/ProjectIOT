import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';
import { Event } from './models/event.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient, public authenticationService: AuthenticationService) {}

  addCategory(){
  }

  denyEvent(eventId: number): Observable<any> {
    const getUserUrl = `http://localhost:8080/events/${eventId}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.delete(getUserUrl, { headers });
  } 

  acceptEvent(eventId: number): Observable<any>{
    const getUserUrl = `http://localhost:8080/events/accept/${eventId}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, { headers });
  }

  getEventsToAcceptance(): Observable<any> {
    const getUserUrl = `http://localhost:8080/events/toAcceptance`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<Event[]>(getUserUrl, { headers });
  }
}
