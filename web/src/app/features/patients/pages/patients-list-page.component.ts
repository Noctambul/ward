import { Component, inject, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { Patient } from '../models/patient';
import { PatientService } from '../services/patient.service';
import { catchError, finalize, of } from 'rxjs';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-patients-list-page',
  templateUrl: './patients-list-page.component.html',
  standalone: true,
  imports: [MatTableModule, MatProgressSpinnerModule, MatButtonModule, MatIconModule],
})
export class PatientsListPageComponent {
  private readonly router = inject(Router);
  private readonly patientService = inject(PatientService);

  readonly displayedColumns: string[] = ['name', 'birthDate', 'gender'];
  readonly loading = signal(false);
  readonly errorMessage = signal('');

  readonly patients = toSignal(
    this.patientService.list().pipe(
      catchError((error) => {
        this.errorMessage.set(error.message);
        return of([] as Patient[]);
      }),
    ),
    { initialValue: [] as Patient[] },
  );

  createPatient() {
    this.router.navigate(['/patients/new']);
  }
}
