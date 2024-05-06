import { AfterViewInit, Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { SwiperContainer } from 'swiper/element';
import { SwiperOptions } from 'swiper/types';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';
import { EventService } from '../event.service';
import { HttpClient } from '@angular/common/http';



@Component({
  selector: 'app-main-site',
  templateUrl: './main-site.component.html',
  styleUrls: ['./main-site.component.scss']
})
export class MainSiteComponent implements OnInit{
  @ViewChild('swiper') swiper!: ElementRef<SwiperContainer>;
  contents1: Event[] = [];
  contents2: Event[] = [];
  contents3: Event[] = [];

  constructor(private router: Router, private eventService: EventService,private http: HttpClient) {
  }

  ngOnInit(){
    this.eventService.getRecomendedEvents().subscribe(data => {
      this.contents1 = data;
    }, error => console.error(error));

    this.eventService.getRecentEvents().subscribe(data => {
      this.contents2 = data;
    }, error => console.error(error));

    this.eventService.getAllEvents().subscribe(data => {
      this.contents3 = data;
    }, error => console.error(error));
  }


  index = 0;
  hoverIndices: { [key: string]: number } = {
    swiper1: -1,
    swiper2: -1,
    swiper3: -1,
  }

  // Swiper
  swiperConfig: SwiperOptions = {
    spaceBetween: 20,
    slidesPerView: 5, 
    freeMode: true,
    watchSlidesProgress: true,
    navigation: true,
  }

  ngAfterViewInit() {
    this.swiper.nativeElement.swiper.activeIndex = this.index;
  }

  setHover(swiperId: string, index: number): void {
    this.hoverIndices[swiperId] = index;
  }

  resetHover(swiperId: string): void {
    this.hoverIndices[swiperId] = -1;
  }

  slideChange(swiper: any) {
    this.index = swiper.detail[0].activeIndex;
  }

  navigateToDescriptionSite(card: Event): void {
    this.eventService.setCurrentEvent(card);
    this.router.navigate(['/event-details', card.id]);
  }
}