import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUser = new BehaviorSubject<User | null>(this.getCurrentUser());
  constructor(private http: HttpClient, public authenticationService: AuthenticationService) {}
  
  getUserFromDatabase(): Observable<any> {
    const username = this.getUsername();
    const getUserUrl = `http://localhost:8080/users/name/${username}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User>(getUserUrl, { headers });
  }

  getUserFromDatabaseByName(name: string): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/name/${name}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User>(getUserUrl, { headers });
  }

  getAllUsers(): Observable<User[]>{
    const getUserUrl = `http://localhost:8080/users`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User[]>(getUserUrl, { headers });
  }

  getAllUsersFiltered(name: string, mail: string, admin: boolean, mod: boolean, ver: boolean, nonver: boolean): Observable<User[]> {
    const params = {name: name, mail: mail, admin: admin.toString(), mod: mod.toString(), ver: ver.toString(), nonver: nonver.toString()};
    const getUserUrl = `http://localhost:8080/users/filter`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User[]>(getUserUrl, { headers, params });
  }

  setCurrentUser(user: User): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('username', user.name); 
  }

  removeCurrentUser(): void {
    this.currentUser.next(null);
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userToken');
    localStorage.removeItem('username');
  }

  addUser(user: any): Observable<any> {
    return this.http.post('http://localhost:8080/unauthorized/addUser', user);
  }

  getLastID(): Observable<any> {
    return this.http.get('http://localhost:8080/unauthorized/user/last');
  }

  updateUserPermissions(id: number, permissionLevel: number): void {
    const getUserUrl = `http://localhost:8080/users/${id}/updatePermissions`;
    const params = permissionLevel;
    const headers = this.authenticationService.getHeadersWithToken()
    this.http.put(getUserUrl, params, {headers}).subscribe();
  }

  updateUserPassword(id: number, password: string): void {
    const getUserUrl = `http://localhost:8080/users/${id}/updatePassword`;
    const params = password;
    const headers = this.authenticationService.getHeadersWithToken()
    this.http.put(getUserUrl, params, {headers}).subscribe();
  }

  requestPasswordReset(mail: string): Observable<number> {
    return this.http.post<number>(`http://localhost:8080/unauthorized/sendResetToken`, mail);
  }

  confirmRequestPasswordReset(token: string, password: string): Observable<boolean> {
    return this.http.put<boolean>(`http://localhost:8080/unauthorized/resetPasswordConfirm/${token}`, password);
  }


  //Zarządzanie Subskrypcją
  
  subscribeEvent(eventId: number){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribeEvent/${eventId}`;
    return this.http.patch(getUserUrl, null, this.authenticationService.getOptionsWithToken());
  }


  unsubscribeEvent(eventId: number){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/unsubscribeEvent/${eventId}`;
    return this.http.patch(getUserUrl, null, this.authenticationService.getOptionsWithToken());
  }


  subscribeCategory(eventId: number[]){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribeCategories`;
    return this.http.patch(getUserUrl, eventId, this.authenticationService.getOptionsWithToken());
  }

  unsubscribeCategory(categoryId: number){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/unsubscribeCategory/${categoryId}`;
    return this.http.patch(getUserUrl,  null, this.authenticationService.getOptionsWithToken());
  }

  getSubscribedCategories(): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribedCategories`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }
  getSubscribedCategoriesById(id: number): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${id}/subscribedCategories`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }

  getSubscribedEvents(): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribedEvents`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }
  getSubscribedEventsById(id: number): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${id}/subscribedEvents`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }


  getCurrentUser(): User {
    try {
      const userJson = localStorage.getItem('currentUser');
      if (userJson) {
        return JSON.parse(userJson) as User;
      }
    } catch (error) {
      console.error('Error parsing user data from localStorage', error);
    }
    return {id:0, name: "Adam" , mail: "a@mail.com", permissionLevel: 'None', token: "", subscribedEvents: []};
  }

  private getUserToken(): string | null {
    return localStorage.getItem('userToken');
  }

  private getUsername(): string | null {
    return localStorage.getItem('username');
  }
}

