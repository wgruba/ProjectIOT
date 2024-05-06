import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { ActivatedRoute } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';


@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent {
  warning: string = "";
  password: string = "";
  password_repeated: string = "";
  zmieniono: boolean = false;
  token: string = "";

  constructor(
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    ) {
      this.activatedRoute.params.subscribe(params => {
        const t = params['token'];
        this.token = t;
      });
      this.zmieniono = false;
    }

  updateWarning() {
    const errors = this.validatePassword(this.password, this.password_repeated);
    this.warning = errors.join("\r\n");
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
  onYesClick(): void {
    if (this.isPasswordValid()) {
      this.userService.confirmRequestPasswordReset(this.token, this.password).subscribe(Response => {
        if (Response) {
          this.zmieniono = true;
        }
      });
    }
  }
}
