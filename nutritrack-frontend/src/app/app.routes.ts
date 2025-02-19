import { Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {authGuard} from './core/auth.guard';
import {LoginComponent} from './pages/auth/login/login.component';
import {RegisterComponent} from './pages/auth/register/register.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent }, //, canActivate: [authGuard]
  { path: '**', redirectTo: '' }
];
