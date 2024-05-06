import { CUSTOM_ELEMENTS_SCHEMA,NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { register } from 'swiper/element/bundle';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainSiteComponent } from './main-site/main-site.component';
import { UserHeaderComponent } from './user-header/user-header.component';
import { UserFooterComponent } from './user-footer/user-footer.component';
import { SwiperDirective } from './swiper.directive';
import { RegisterSiteComponent } from './register-site/register-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { DescriptionPageComponent } from './description-page/description-page.component';
import { EventService } from './event.service';
import { AuthenticationService } from './authentication.service';
import { UserService } from './user.service';
import { MapComponent } from './map/map.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { AboutUsComponent } from './about-us/about-us.component';
import { UserHelpComponent } from './user-help/user-help.component';
import { AddEventSiteComponent } from './add-event-site/add-event-site.component';
import { UserProfileAsideComponent } from './user-profile-aside/user-profile-aside.component';
import { UserSearchingPageComponent } from './user-searching-page/user-searching-page.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserEventsComponent } from './user-events/user-events.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModeratorAcceptationSiteComponent } from './moderator-acceptation-site/moderator-acceptation-site.component';
import { ModeratorAcceptationDetailsSiteComponent } from './moderator-acceptation-details-site/moderator-acceptation-details-site.component';
import { ModeratorUsersSearchSiteComponent } from './moderator-users-search-site/moderator-users-search-site.component';
import { UserSubscriptionsComponent } from './user-subscriptions/user-subscriptions.component';
import {MatDialogModule} from '@angular/material/dialog';
import { ConfirmationDialogComponentComponent } from './confirmation-dialog-component/confirmation-dialog-component.component';
import { AddingCategoriesModalComponent } from './adding-categories-modal/adding-categories-modal.component';
import { ChangingPasswordModalComponent } from './changing-password-modal/changing-password-modal.component';
import { EditingEventModalComponent } from './editing-event-modal/editing-event-modal.component';
import { EditEventComponent } from './edit-event/edit-event.component';
import { AdminCategoryComponent } from './admin-category/admin-category.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { AdminEventsComponent } from './admin-events/admin-events.component';
import { AdminSubsComponent } from './admin-subs/admin-subs.component';
import { ModProfileComponent } from './mod-profile/mod-profile.component';
import { ForgottenPasswordModalComponent } from './forgotten-password-modal/forgotten-password-modal.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';


register();

@NgModule({
  declarations: [
    AppComponent,
    MainSiteComponent,
    LoginSiteComponent,
    RegisterSiteComponent,
    UserHeaderComponent,
    UserFooterComponent,
    SwiperDirective,
    DescriptionPageComponent,
    MapComponent,
    AboutUsComponent,
    UserHelpComponent,
    AddEventSiteComponent,
    UserProfileAsideComponent,
    UserSearchingPageComponent,
    UserProfileComponent,
    UserEventsComponent,
    ModeratorAcceptationSiteComponent,
    ModeratorAcceptationDetailsSiteComponent,
    ModeratorUsersSearchSiteComponent,
    UserSubscriptionsComponent,
    ConfirmationDialogComponentComponent,
    AddingCategoriesModalComponent,
    ChangingPasswordModalComponent,
    EditingEventModalComponent,
    EditEventComponent,
    AdminCategoryComponent,
    AdminProfileComponent,
    AdminEventsComponent,
    AdminSubsComponent,
    ModProfileComponent,
    ForgottenPasswordModalComponent,
    PasswordResetComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    GoogleMapsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  providers: [EventService, UserService, AuthenticationService],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class AppModule { }
