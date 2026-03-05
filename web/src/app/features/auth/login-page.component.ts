import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormBuilder } from '@angular/forms';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,
    MatFormFieldModule, MatInputModule, MatButtonModule,
    MatCardModule, MatProgressSpinnerModule],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);

  loading = false
  serverError = '';

  loginForm = inject(FormBuilder).group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  get emailCtrl() { return this.loginForm.get('email')! }
  get passwordCtrl() { return this.loginForm.get('password')! }

  ngOnInit() {
    this.logout();
  }

  onSubmit() {
    if (!this.loginForm.valid) {
      return;
    }

    this.startLoading()
    this.authService.login(this.loginForm.value.email!, this.loginForm.value.password!)
      .pipe( finalize(() => {
        this.stopLoading();
      }))
      .subscribe({
        next: () => this.redirectIfAuthenticated(),
        error: (err) => {
          if (err.status === 401) {
            this.serverError = 'Identifiants invalides';
          } else {
            this.serverError = 'Une erreur est survenue';
          }
        }
      });
  }

  loginAsDoctor() {
    this.loginForm.patchValue({
      email: 'doctor@wardcare.fr',
      password: '123456'
    });
  }

  private logout() {
    this.authService.logout();
  }

  private startLoading() {
    this.loading = true;
    this.loginForm.disable();
  }

  private stopLoading() {
    this.loading = false;
    this.loginForm.enable();
  }

  private redirectIfAuthenticated() {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/patients']);
    }
  }
}
