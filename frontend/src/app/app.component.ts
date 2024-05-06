import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'IwentHub';
  constructor(public router: Router, private titleService: Title) {
    this.titleService.setTitle('IwentHub');
  }
}
