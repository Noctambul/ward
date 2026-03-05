import { Component, inject, OnInit, signal } from '@angular/core';
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
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);

  readonly loading = signal(false);
  readonly serverError = signal('');

  readonly loginForm = inject(FormBuilder).group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  readonly emailCtrl = this.loginForm.controls.email
  readonly passwordCtrl = this.loginForm.controls.password

  ngOnInit() {
    this.logout();
  }

  onSubmit() {
    this.serverError.set("");

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading.set(true);

    const { email, password } = this.loginForm.getRawValue();

    this.authService.login(email!, password!)
      .pipe( finalize(() => this.loading.set(false)))
      .subscribe({
        next: () => this.redirectIfAuthenticated(),
        error: (err) => {
          if (err.status === 401) {
            this.serverError.set('Identifiants invalides');
          } else {
            this.serverError.set('Une erreur est survenue');
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

  private redirectIfAuthenticated() {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/patients']);
    }
  }
}
