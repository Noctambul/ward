import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login-page.component').then(m => m.LoginPageComponent)
  },
  {
    path: 'patients',
    loadComponent: () => import('./features/patients/patients-list-page.component').then(m => m.PatientsListPageComponent)
  },
  {
    path: 'patients/new',
    loadComponent: () => import('./features/patients/patient-create-page.component').then(m => m.PatientCreatePageComponent)
  },
  { path: '**', redirectTo: '/login' }
];
