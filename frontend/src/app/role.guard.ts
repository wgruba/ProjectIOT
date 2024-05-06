import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { UserService } from './user.service'; // Adjust the import path as needed

interface RouteData {
  allowedRoles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const routeData = route.data as RouteData;
    const allowedRoles = routeData.allowedRoles;
    console.log(allowedRoles)
    const user = this.userService.getCurrentUser();

    if (user && allowedRoles.includes(user.permissionLevel)) {
      return true;
    }

    this.router.navigate(['/login-site']);
    return false;
  }
}
