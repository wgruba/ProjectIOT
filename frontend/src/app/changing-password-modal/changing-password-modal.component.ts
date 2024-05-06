import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-changing-password-modal',
  templateUrl: './changing-password-modal.component.html',
  styleUrls: ['./changing-password-modal.component.scss']
})
export class ChangingPasswordModalComponent {
  password: string = "";
  password_repeated: string = "";
  warning: string = "";
  login: string = "";
  email: string = "";
  id: number = 0;
  message: string = "";

  
  constructor(
    public dialogRef: MatDialogRef<ChangingPasswordModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

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

  onNoClick(): void {
    this.dialogRef.close(null);
  }

  onYesClick(): void {
    if (this.isPasswordValid()) {
      this.dialogRef.close(this.password);
    }
  }

}

export interface DialogData {
  title: string;
  message: string;
}