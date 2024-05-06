import { Component } from '@angular/core';
import { User } from '../models/user.model';
import { UserService } from '../user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.scss']
})
export class AdminProfileComponent {
  user!: User;
  areYouSureVisible = false;
  permissionChange = 0;
  constructor(private route: ActivatedRoute, public userService: UserService, private location: Location) {
  }

  ngOnInit(): void {
    const userName = this.route.snapshot.params['name'];
    this.userService.getUserFromDatabaseByName(userName).subscribe(data => {
      this.user = data;
    }, error => console.error(error));
  }

  changePermission(type: number): void {
    this.permissionChange = type;
    this.areYouSureVisible = true;
    
  }

  changePermissionConfirm(): void {
    this.userService.updateUserPermissions(this.user.id, this.permissionChange);
    this.areYouSureVisible = false;
    setTimeout(() => {
    window.location.reload();}, 500);
  }

  changePermissionCancel(): void {
    this.areYouSureVisible = false;
  }
}
