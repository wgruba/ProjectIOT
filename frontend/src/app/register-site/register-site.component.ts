import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';



@Component({
  selector: 'app-register-site',
  templateUrl: './register-site.component.html',
  styleUrls: ['./register-site.component.scss']
})



export class RegisterSiteComponent {
  password: string = "";
  password_repeated: string = "";
  warning: string = "";
  login: string = "";
  email: string = "";
  id: number = 0;
  message: string = "";

  constructor(public userService: UserService, private snackBar: MatSnackBar,private router: Router) {
    
  }

  validatePassword(p: string, p2: string): string[] {
    const errors: string[] = [];
  
    if (p.length < 8) {
      errors.push("Hasło musi mieć co najmniej 8 znaków.");
    }
    if (!/[a-z]/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jedną małą literę.");
    }
    if (!/[A-Z]/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jedną dużą literę.");
    }
    if (!/\d/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jedną cyfrę.");
    }
    if (!/[ !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]/.test(p)) {
      errors.push("Hasło musi zawierać co najmniej jeden znak specjalny.");
    }
    if (p!==p2) {
      errors.push("Hasła muszą być takie same.");
    }

    return errors;
  }

  isPasswordValid(): boolean {
    return this.validatePassword(this.password, this.password_repeated).length === 0;
  }

  registerUser(event: Event) {
    if(this.isPasswordValid()){
      this.userService.getLastID().subscribe(response => {
        this.id = response + 1;
        event.preventDefault();
        const user = {
          id: this.id,
          name: this.login,
          mail: this.email,
          password: this.password,
          permissionLevel: "UNVERIFIED_USER",
          subscribedEvents: [],
          subscribedCategories: [],
        };
        this.userService.addUser(user).subscribe(
          response => {
            this.snackBar.open("Zarejestrowano pomyślnie", 'Zamknij', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top',
            });
            this.router.navigate(['/login-site']);
          },
          (error: HttpErrorResponse) => {
            if(error.status === 409) {
              const errorMessage = error.error?.message || "Użytkownik o takiej nazwie lub mailu istnieje spróbuj ponownie";
              this.snackBar.open(errorMessage, 'Zamknij', {
                duration: 3000,
                horizontalPosition: 'right',
                verticalPosition: 'top',
              });
            } else {
              this.snackBar.open("Coś poszło nie tak spróbuj ponownie", 'Zamknij', {
                duration: 3000,
                horizontalPosition: 'right',
                verticalPosition: 'top',
              });
            }
            console.error('Error registering user', error);
          }
        );
      });
    } else {
      this.updateWarning();
    }
  }
  
  
  updateWarning() {
    const errors = this.validatePassword(this.password, this.password_repeated);
    this.warning = errors.join("\r\n");
  }
}

