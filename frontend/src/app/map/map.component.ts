import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit{
  @Input() location!: string;
  private map!: L.Map;

  constructor(private http: HttpClient) {}

  ngAfterViewInit(): void {
    this.locatePlace(this.location);
  }

  private locatePlace(place: string): void {
    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${place}`;
    this.http.get<any[]>(url).subscribe(data => {
      if (data && data.length > 0) {
        const { lat, lon } = data[0];
        this.initMap(lat, lon, place);
      } else {
        console.log('No results found for the specified place.');
      }
    }, error => {
      console.error('There was an error with the geocoding request:', error);
    });
  }

  private initMap(latitude: number, longitude: number, placeName: string): void {
    this.map = L.map('map').setView([latitude, longitude], 13);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    L.marker([latitude, longitude]).addTo(this.map)
      .bindPopup(placeName)
      .openPopup();
  }


  searchPlace(place: string): void {
    if (!place.trim()) {
      // If the search term is empty, return without doing anything
      return; 
    }

    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(place)}`;

    this.http.get<any[]>(url).subscribe(data => {
      if (data && data.length > 0) {
        const { lat, lon } = data[0];
        this.map.setView([lat, lon], 13);

        const marker = L.marker([lat, lon]).addTo(this.map)
          .bindPopup(place)
          .openPopup();
      } else {
        // Handle the case where no results are found
        console.log('No results found for the specified place.');
      }
    }, error => {
      // Handle HTTP errors here
      console.error('There was an error with the geocoding request:', error);
    });
  }
}