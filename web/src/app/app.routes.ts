import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login-page.component').then(m => m.LoginPageComponent)
  },
  {
    path: 'patients',
    canActivate: [authGuard],
    loadComponent: () => import('./features/patients/patients-list-page.component').then(m => m.PatientsListPageComponent)
  },
  {
    path: 'patients/new',
    canActivate: [authGuard],
    loadComponent: () => import('./features/patients/patient-create-page.component').then(m => m.PatientCreatePageComponent)
  },
  { path: '**', redirectTo: '/login' }
];
