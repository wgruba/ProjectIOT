import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../user.service';

@Component({
  selector: 'app-forgotten-password-modal',
  templateUrl: './forgotten-password-modal.component.html',
  styleUrls: ['./forgotten-password-modal.component.scss']
})
export class ForgottenPasswordModalComponent {

  email: string = "";

  constructor(
    public dialogRef: MatDialogRef<ForgottenPasswordModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private userService: UserService
  ) {}

  onNoClick(): void {
    this.dialogRef.close(null);
  }

  onYesClick(): void {
    this.userService.requestPasswordReset(this.email).subscribe(response => {
      this.dialogRef.close(response);
    });
  }

}
export interface DialogData {
  title: string;
  message: string;
}