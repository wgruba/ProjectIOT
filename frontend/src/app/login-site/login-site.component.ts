import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Event } from '../models/event.model';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { MatDialog } from '@angular/material/dialog';
import { ForgottenPasswordModalComponent } from '../forgotten-password-modal/forgotten-password-modal.component';


@Component({
  selector: 'app-login-site',
  templateUrl: './login-site.component.html',
  styleUrls: ['./login-site.component.scss']
})
export class LoginSiteComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  subscribedEvents!: Event[]

  constructor(private authService: AuthenticationService,
    private router: Router,
    public userService: UserService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    ) {}

  onSubmit() {
    if (this.username && this.password) {
      this.authService.login(this.username, this.password).subscribe({
        next: (response) => {
          this.login(response)
        },
        error: (error) => {
          this.errorMessage = 'Niepoprawna Nazwa użytkownika lub hasło';
        }
      });
  }
}

onForgot() {
  const dialogRef = this.dialog.open(ForgottenPasswordModalComponent, {
    width: '350px',
    data: {title: 'Resetuj hasło', message: 'Podaj adres email powiązany z kontem'}
  });
  dialogRef.afterClosed().subscribe(result => {
    if(result != null) {
      if(result == 0) {
        this._snackBar.open("Link do resetu hasła został wysłany na podany email", 'Zamknij', {duration: 5000})
      }
      else {
        this._snackBar.open("Nie znaleziono użytkownika o podanym adresie email, bądź wystąpił problem.", 'Zamknij', {duration: 5000})
      }
    }
  });
}

login(response: User){
  if(response && response.token) {  
    this.authService.setIsLoggedIn(true); 
    localStorage.setItem('userToken', response.token);
    localStorage.setItem('username', this.username)
    this.userService.getUserFromDatabase().subscribe(data => {
      this.userService.getSubscribedEvents().subscribe(response => {
        this.subscribedEvents = response;
        const event_ids = this.subscribedEvents.map(event => event.id);
        data['subscribedEvents'] = event_ids
        this.userService.setCurrentUser(data);
        this.router.navigate(['/home']);
      });
      this.snackBar.open("Zalogowano pomyślnie", 'Zamknij', {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'top', });
    }, error => console.error(error));
  } else {
    this.errorMessage = 'Login failed: Invalid username or password';
  }
}
}
