import { Component } from '@angular/core';
import { User } from '../models/user.model';
import { UserService } from '../user.service';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../models/Category.model';
import { MatDialog } from '@angular/material/dialog';
import { Event } from '../models/event.model';


@Component({
  selector: 'app-admin-subs',
  templateUrl: './admin-subs.component.html',
  styleUrls: ['./admin-subs.component.scss']
})
export class AdminSubsComponent {
  user!: User;
  subscribedCategoriesList! : Category[];
  subscribedEventsList! : Event[];

  constructor(private route: ActivatedRoute, public userService: UserService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    const userName = this.route.snapshot.params['name'];
    this.userService.getUserFromDatabaseByName(userName).subscribe(data => {
      this.user = data;
      this.userService.getSubscribedEventsById(data.id).subscribe(response => {
        this.subscribedEventsList = response;
      });
      this.userService.getSubscribedCategoriesById(data.id).subscribe(response => {
        this.subscribedCategoriesList = response
      });
    }, error => console.error(error));
  }


}
