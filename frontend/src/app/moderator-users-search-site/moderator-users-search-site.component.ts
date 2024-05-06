import { Component, OnInit } from '@angular/core';
import { User } from '../models/user.model';
import { Router } from '@angular/router';
import { EventService } from '../event.service';
import { UserService } from '../user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-moderator-users-search-site',
  templateUrl: './moderator-users-search-site.component.html',
  styleUrls: ['./moderator-users-search-site.component.scss']
})
export class ModeratorUsersSearchSiteComponent implements OnInit{
  isAdmin: boolean = false;
  users: User[] = []
  filterForm: FormGroup;

  constructor(private router: Router, public userService: UserService, private formBuilder: FormBuilder) {
    this.filterForm = this.formBuilder.group({
      admini: true,
      moderatorzy: true,
      klienciVer: true,
      klienciNieVer: true,
      email: '',
      imie: '',
    })
  }

  ngOnInit(): void {
    console.log(this.filterForm.value)
    this.userService.getAllUsersFiltered(this.filterForm.value.imie, this.filterForm.value.email, this.filterForm.value.admini, this.filterForm.value.moderatorzy, this.filterForm.value.klienciVer, this.filterForm.value.klienciNieVer).subscribe(data => {
      this.users = data;
    }, error => console.error(error));
  }

  applyFilters(){
    this.ngOnInit();
  }

  showDetails(card: string): void {
    this.router.navigate(['/admin-profile', card]);
  }
}
