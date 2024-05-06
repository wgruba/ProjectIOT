import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainSiteComponent } from './main-site/main-site.component';
import { LoginSiteComponent } from './login-site/login-site.component';
import { RegisterSiteComponent } from './register-site/register-site.component';
import { DescriptionPageComponent } from './description-page/description-page.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { UserHelpComponent } from './user-help/user-help.component';
import { AddEventSiteComponent } from './add-event-site/add-event-site.component';
import { UserSearchingPageComponent } from './user-searching-page/user-searching-page.component';
import { AuthGuard } from './auth.guard';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserEventsComponent } from './user-events/user-events.component';
import { RoleGuard } from './role.guard';
import { ModeratorAcceptationSiteComponent } from './moderator-acceptation-site/moderator-acceptation-site.component';
import { ModeratorAcceptationDetailsSiteComponent } from './moderator-acceptation-details-site/moderator-acceptation-details-site.component';
import { ModeratorUsersSearchSiteComponent } from './moderator-users-search-site/moderator-users-search-site.component';
import { UserSubscriptionsComponent } from './user-subscriptions/user-subscriptions.component';
import { EditEventComponent } from './edit-event/edit-event.component';
import { AdminCategoryComponent } from './admin-category/admin-category.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { AdminEventsComponent } from './admin-events/admin-events.component';
import { AdminSubsComponent } from './admin-subs/admin-subs.component';
import { ModProfileComponent } from './mod-profile/mod-profile.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: MainSiteComponent,  data: { allowedRoles:  ['VERIFIED_USER', 'UNVERIFIED_USER'] }},
  { path: 'login-site', component:LoginSiteComponent},
  { path: 'register-site', component:RegisterSiteComponent},
  { path: 'event-details/:id', component: DescriptionPageComponent},
  { path: 'o-nas', component:AboutUsComponent},
  { path: 'pomoc', component:UserHelpComponent},
  { path: 'add-event', component: AddEventSiteComponent ,canActivate: [AuthGuard]},
  { path: 'edit-event', component: EditEventComponent ,canActivate: [AuthGuard]},
  { path: 'event-searching', component: UserSearchingPageComponent},
  { path: 'user-profile', component: UserProfileComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['VERIFIED_USER', 'UNVERIFIED_USER'] } },
  { path: 'mod-profile', component: ModProfileComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['MODERATOR', 'ADMIN'] }},
  { path: 'user-events', component: UserEventsComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['VERIFIED_USER', 'UNVERIFIED_USER'] } },
  { path: 'user-subs', component: UserSubscriptionsComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['VERIFIED_USER', 'UNVERIFIED_USER'] } },
  { path: 'admin-acceptance', component: ModeratorAcceptationSiteComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['MODERATOR', 'ADMIN'] } },
  { path: 'admin-event-details/:id', component: ModeratorAcceptationDetailsSiteComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['MODERATOR', 'ADMIN'] } },
  { path: 'admin-users', component: ModeratorUsersSearchSiteComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['MODERATOR', 'ADMIN'] } },
  { path: 'category-management', component: AdminCategoryComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['ADMIN'] } },
  { path: 'admin-profile/:name', component: AdminProfileComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['ADMIN'] } },
  { path: 'admin-events/:name', component: AdminEventsComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['ADMIN'] } },
  { path: 'admin-subs/:name', component: AdminSubsComponent, canActivate: [AuthGuard, RoleGuard],  data: { allowedRoles:  ['ADMIN'] } },
  { path: 'resetPassword/:token', component: PasswordResetComponent},
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
