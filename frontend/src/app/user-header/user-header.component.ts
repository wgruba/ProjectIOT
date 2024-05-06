import { Component, OnInit} from '@angular/core';
import { Category } from '../models/Category.model';
import { SelectedItem } from '../models/selectedItem.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';
import { SelectedLocation } from '../models/selectedLocation.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FilterSearchService } from '../filter-search.service';
import { Subscription } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponentComponent } from '../confirmation-dialog-component/confirmation-dialog-component.component';
import { CategoryService } from '../category.service';
import { FilteredEventParameters } from '../models/FilteredEventParameters.model';


@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent implements OnInit {
  categories !: Category[];
  localisations = [
    {
      id: 1,
      name: 'Dolnośląskie',
      subcategories: [
        { id: 101, name: 'Wrocław' },
        { id: 102, name: 'Wałbrzych' },
        { id: 103, name: 'Legnica' },
        { id: 104, name: 'Jelenia Góra' },
        { id: 105, name: 'Lubin' },
        { id: 106, name: 'Głogów' },
        { id: 107, name: 'Świdnica' },
        { id: 108, name: 'Bolesławiec' },
        { id: 109, name: 'Oleśnica' },
        { id: 110, name: 'Dzierżoniów' },
        { id: 111, name: 'Zgorzelec' }
      ]
    },
    {
      id: 2,
      name: 'Kujawsko-Pomorskie',
      subcategories: [
        { id: 201, name: 'Bydgoszcz' },
        { id: 202, name: 'Toruń' },
        { id: 203, name: 'Włocławek' },
        { id: 204, name: 'Grudziądz' },
        { id: 205, name: 'Inowrocław' },
        { id: 206, name: 'Brodnica' },
        { id: 207, name: 'Chełmno' },
        { id: 208, name: 'Świecie' },
        { id: 209, name: 'Tuchola' },
        { id: 210, name: 'Nakło nad Notecią' },
        { id: 211, name: 'Rypin' }
      ]
    },
    {
      id: 3,
      name: 'Lubelskie',
      subcategories: [
        { id: 301, name: 'Lublin' },
        { id: 302, name: 'Chełm' },
        { id: 303, name: 'Zamość' },
        { id: 304, name: 'Biała Podlaska' },
        { id: 305, name: 'Puławy' },
        { id: 306, name: 'Świdnik' },
        { id: 307, name: 'Kraśnik' },
        { id: 308, name: 'Lubartów' },
        { id: 309, name: 'Biłgoraj' },
        { id: 310, name: 'Łuków' },
        { id: 311, name: 'Krasnystaw' },
        { id: 312, name: 'Szczebrzeszyn' },

      ]
    },
    {
      id: 4,
      name: 'Lubuskie',
      subcategories: [
        { id: 401, name: 'Gorzów Wielkopolski' },
        { id: 402, name: 'Zielona Góra' },
        { id: 403, name: 'Nowa Sól' },
        { id: 404, name: 'Żary' },
        { id: 405, name: 'Żagań' },
        { id: 406, name: 'Sulechów' },
        { id: 407, name: 'Kostrzyn nad Odrą' },
        { id: 408, name: 'Międzyrzecz' },
        { id: 409, name: 'Świebodzin' },
        { id: 410, name: 'Gubin' },
        { id: 411, name: 'Wschowa' }
      ]
    },
    {
      id: 5,
      name: 'Łódzkie',
      subcategories: [
        { id: 501, name: 'Łódź' },
        { id: 502, name: 'Piotrków Trybunalski' },
        { id: 503, name: 'Skierniewice' },
        { id: 504, name: 'Zgierz' },
        { id: 505, name: 'Bełchatów' },
        { id: 506, name: 'Sieradz' },
        { id: 507, name: 'Radomsko' },
        { id: 508, name: 'Pabianice' },
        { id: 509, name: 'Tomaszów Mazowiecki' },
        { id: 510, name: 'Wieluń' }
      ]
    },
    {
      id: 6,
      name: 'Małopolskie',
      subcategories: [
        { id: 601, name: 'Kraków' },
        { id: 602, name: 'Tarnów' },
        { id: 603, name: 'Nowy Sącz' },
        { id: 604, name: 'Oświęcim' },
        { id: 605, name: 'Chrzanów' },
        { id: 606, name: 'Olkusz' },
        { id: 607, name: 'Nowy Targ' },
        { id: 608, name: 'Zakopane' },
        { id: 609, name: 'Bochnia' },
        { id: 610, name: 'Gorlice' }
      ]
    },
    {
      id: 7,
      name: 'Mazowieckie',
      subcategories: [
        { id: 701, name: 'Warszawa' },
        { id: 702, name: 'Radom' },
        { id: 703, name: 'Płock' },
        { id: 704, name: 'Siedlce' },
        { id: 705, name: 'Pruszków' },
        { id: 706, name: 'Ostrołęka' },
        { id: 707, name: 'Legionowo' },
        { id: 708, name: 'Ciechanów' },
        { id: 709, name: 'Sochaczew' },
        { id: 710, name: 'Otwock' }
      ]
    },
    {
      id: 8,
      name: 'Opolskie',
      subcategories: [
        { id: 801, name: 'Opole' },
        { id: 802, name: 'Kędzierzyn-Koźle' },
        { id: 803, name: 'Nysa' },
        { id: 804, name: 'Brzeg' },
        { id: 805, name: 'Kluczbork' },
        { id: 806, name: 'Prudnik' },
        { id: 807, name: 'Strzelce Opolskie' },
        { id: 808, name: 'Głubczyce' },
        { id: 809, name: 'Krapkowice' },
        { id: 810, name: 'Namysłów' }      
      ]
    },
    {
      id: 9,
      name: 'Podkarpackie',
      subcategories: [
        { id: 901, name: 'Rzeszów' },
        { id: 902, name: 'Przemyśl' },
        { id: 903, name: 'Stalowa Wola' },
        { id: 904, name: 'Mielec' },
        { id: 905, name: 'Tarnobrzeg' },
        { id: 906, name: 'Krosno' },
        { id: 907, name: 'Sanok' },
        { id: 908, name: 'Jasło' },
        { id: 909, name: 'Dębica' },
        { id: 910, name: 'Łańcut' },
        { id: 911, name: 'Rudnik nad Sanem' }
      ]
    },
    {
      id: 10,
      name: 'Podlaskie',
      subcategories: [
        { id: 1001, name: 'Białystok' },
        { id: 1002, name: 'Suwałki' },
        { id: 1003, name: 'Łomża' },
        { id: 1004, name: 'Augustów' },
        { id: 1005, name: 'Zambrów' },
        { id: 1006, name: 'Bielsk Podlaski' },
        { id: 1007, name: 'Hajnówka' },
        { id: 1008, name: 'Grajewo' },
        { id: 1009, name: 'Siemiatycze' },
        { id: 1010, name: 'Sokółka' },
        { id: 1011, name: 'Nowe Aleksandrowo' }
      ]
    },
    {
      id: 11,
      name: 'Pomorskie',
      subcategories: [
        { id: 1101, name: 'Gdańsk' },
        { id: 1102, name: 'Gdynia' },
        { id: 1103, name: 'Słupsk' },
        { id: 1104, name: 'Tczew' },
        { id: 1105, name: 'Starogard Gdański' },
        { id: 1106, name: 'Wejherowo' },
        { id: 1107, name: 'Rumia' },
        { id: 1108, name: 'Chojnice' },
        { id: 1109, name: 'Malbork' },
        { id: 1110, name: 'Kwidzyn' }
      ]
    },
    {
      id: 12,
      name: 'Śląskie',
      subcategories: [
        { id: 1201, name: 'Katowice' },
        { id: 1202, name: 'Częstochowa' },
        { id: 1203, name: 'Sosnowiec' },
        { id: 1204, name: 'Gliwice' },
        { id: 1205, name: 'Zabrze' },
        { id: 1206, name: 'Bytom' },
        { id: 1207, name: 'Rybnik' },
        { id: 1208, name: 'Tychy' },
        { id: 1209, name: 'Dąbrowa Górnicza' },
        { id: 1210, name: 'Chorzów' }
      ]
    },
    {
      id: 13,
      name: 'Świętokrzyskie',
      subcategories: [
        { id: 1301, name: 'Kielce' },
        { id: 1302, name: 'Ostrowiec Świętokrzyski' },
        { id: 1303, name: 'Starachowice' },
        { id: 1304, name: 'Sandomierz' },
        { id: 1305, name: 'Skarżysko-Kamienna' },
        { id: 1306, name: 'Końskie' },
        { id: 1307, name: 'Busko-Zdrój' },
        { id: 1308, name: 'Staszów' },
        { id: 1309, name: 'Włoszczowa' },
        { id: 1310, name: 'Jędrzejów' }      ]
    },
    {
      id: 14,
      name: 'Warmińsko-Mazurskie',
      subcategories: [
        { id: 1401, name: 'Olsztyn' },
        { id: 1402, name: 'Elbląg' },
        { id: 1403, name: 'Ełk' },
        { id: 1404, name: 'Ostróda' },
        { id: 1405, name: 'Iława' },
        { id: 1406, name: 'Kętrzyn' },
        { id: 1407, name: 'Szczytno' },
        { id: 1408, name: 'Bartoszyce' },
        { id: 1409, name: 'Giżycko' },
        { id: 1410, name: 'Mrągowo' }
      ]
    },
    {
      id: 15,
      name: 'Wielkopolskie',
      subcategories: [
        { id: 1501, name: 'Poznań' },
        { id: 1502, name: 'Kalisz' },
        { id: 1503, name: 'Konin' },
        { id: 1504, name: 'Piła' },
        { id: 1505, name: 'Ostrów Wielkopolski' },
        { id: 1506, name: 'Gniezno' },
        { id: 1507, name: 'Leszno' },
        { id: 1508, name: 'Śrem' },
        { id: 1509, name: 'Jarocin' },
        { id: 1510, name: 'Swarzędz' }
      ]
    },
    {
      id: 16,
      name: 'Zachodniopomorskie',
      subcategories: [
        { id: 1601, name: 'Szczecin' },
        { id: 1602, name: 'Koszalin' },
        { id: 1603, name: 'Stargard' }
      ]
    }
  ];
  selectedCategoryIds: number[] = [];
  selectedSubcategoryIds: number[] = [];  
  selectedLocalisation: SelectedLocation = { localisation: '', sublocalisation: ''}
  showPopup: boolean = false;
  searchForm: FormGroup;
  viewLocation: string = 'Lokalizacja:';
  roleClass: string = '';
  isAdmin: boolean = false;
  isUser: boolean = false ;
  filterParsed: any;

  constructor(private categoryService: CategoryService ,private router: Router, private formBuilder: FormBuilder,public authService: AuthenticationService, public userService: UserService, private _snackBar: MatSnackBar, private filterSearchService: FilterSearchService, public dialog: MatDialog) {

    this.searchForm = this.formBuilder.group({
      name: [''],
      startDate: [''],
      endDate: ['']
    });
  }

  ngOnInit(): void {
    const user = this.userService.getCurrentUser();
    if(user){
      this.isAdmin = user.permissionLevel === 'MODERATOR' || user.permissionLevel === "ADMIN";
      this.isUser = (!this.isAdmin)
    }
    this.roleClass = user.permissionLevel === 'VERIFIED_USER' ? 'admin-header' : 'user-header';
    this.filterSearchService.filteredEvents$.subscribe(events => {
      // Aktualizuj listę eventów na podstawie odpowiedzi
      // Może to być wykorzystane do aktualizacji widoku w komponencie
    });

    this.filterSearchService.filterParameters$.subscribe(filters => {
      // Aktualizuj stan filtrów na podstawie odpowiedzi
      // Może to być wykorzystane do ustawienia domyślnych wartości w formularzach filtrów
    });
    this.categoryService.getCategoriesFromDatabase().subscribe(response => {
      this.categories = response;
    });
  }

  toggleCategorySelection(categoryId: number): void {
    const index = this.selectedCategoryIds.indexOf(categoryId);
    if (index > -1) {
      this.selectedCategoryIds.splice(index, 1);
    } else {
      this.selectedCategoryIds.push(categoryId);
    }
  }

  toggleSubcategorySelection(subcategoryId: number): void {
    const index = this.selectedSubcategoryIds.indexOf(subcategoryId);
    if (index > -1) {
      this.selectedSubcategoryIds.splice(index, 1);
    } else {
      this.selectedSubcategoryIds.push(subcategoryId);
    }
  }


  getSelectedCategoryNames(): string[] {
    return this.selectedCategoryIds.map(categoryId => 
      this.categories.find(category => category.id === categoryId)?.name || ''
    );
  }
  
  getSelectedSubcategoryNames(): string[] {
    return this.selectedSubcategoryIds.map(subcategoryId => {
      for (const category of this.categories) {
        const subcategory = category.subcategories.find(sub => sub.first === subcategoryId);
        if (subcategory) {
          return subcategory.second;
        }
      }
      return '';
    });
  }


  getCategoryByName(name: string): Category | undefined {
    return this.categories.find(category => category.name === name);
  }
  
  getSubcategoryByName(name: string): { categoryId: number, subcategoryId: number } | undefined {
    for (const category of this.categories) {
      const subcategory = category.subcategories.find(sub => sub.second === name);
      if (subcategory) {
        return { categoryId: category.id, subcategoryId: subcategory.first };
      }
    }
    return undefined;
  }
  
  removeSelectedCategory(categoryName: string): void {
    const category = this.getCategoryByName(categoryName);
    if (category) {
      this.selectedCategoryIds = this.selectedCategoryIds.filter(id => id !== category.id);
      // Update UI or backend as necessary
    }
  }
  
  removeSelectedSubcategory(subcategoryName: string): void {
    const subcategoryInfo = this.getSubcategoryByName(subcategoryName);
    if (subcategoryInfo) {
      this.selectedSubcategoryIds = this.selectedSubcategoryIds.filter(id => id !== subcategoryInfo.subcategoryId);
      // Update UI or backend as necessary
    }
  }


  toggleLocation(localisation: string, sublocalisation?: string): void {
    if(localisation === this.selectedLocalisation.localisation && sublocalisation === this.selectedLocalisation.sublocalisation) {
      this.selectedLocalisation = { localisation: '', sublocalisation: '' };
      this.viewLocation = 'Lokalizacja:';
    } else {
      this.selectedLocalisation = { localisation: localisation, sublocalisation: sublocalisation };
      if(sublocalisation != null) {
        this.viewLocation = sublocalisation;
      } else {
        this.viewLocation = localisation;
      }
    }
  }

  togglePopup(): void {
    this.showPopup = !this.showPopup;
  }

  logout(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponentComponent, {
      width: '350px',
      data: {title: 'Potwierdzenie', message: 'Czy na pewno chcesz się wylogować'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.userService.removeCurrentUser();
        this.authService.logout();
        this.router.navigate(['/login-site']);      }
    });
  }

  onSearch(): void {
    const filterData = localStorage.getItem('filter')
    if(filterData == null){
      this.filterParsed = JSON.stringify({historyczne: false, rezerwacja: "0", koszt: "0", wiek: "0", miejscaMin: [null, [Validators.min(0)]], miejscaMax: [null, [Validators.min(0)]]})
    }
    else {
      this.filterParsed = JSON.parse(filterData)
    }
    let localistaion: string = '' ;
    if(this.selectedLocalisation.sublocalisation != null){
      localistaion = this.selectedLocalisation.sublocalisation;
    }
    else  {localistaion = this.selectedLocalisation.localisation }
    let filteredEventParameters: FilteredEventParameters = {
      name: this.searchForm.value.name,
      categoryList: [...this.selectedCategoryIds, ...this.selectedSubcategoryIds],
      localisation: localistaion,
      startDate: this.searchForm.value.startDate,
      endDate: this.searchForm.value.endDate,
      isFinished: false,
      reservation: 2,
      isFree: 2,
      ageGroup: 'OVER16'  
    }
    this.filterSearchService.getFilteredEvents(filteredEventParameters).subscribe(response => {
        this.filterSearchService.updateFilteredEvents(response);
        this.filterSearchService.updateFilterParameters(filteredEventParameters);
        this.router.navigate(['/event-searching']);
    });
  }
}
