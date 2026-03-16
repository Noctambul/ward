import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient';
import { environment } from '../../../../environments/environment';

const PATIENTS_URL = environment.apiV1 + '/patients';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private http = inject(HttpClient);

  list(): Observable<Patient[]> {
    return this.http.get<Patient[]>(PATIENTS_URL);
  }

  create(patient: Omit<Patient, 'id'>): Observable<Patient> {
    return this.http.post<Patient>(PATIENTS_URL, patient);
  }
}
