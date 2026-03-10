import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patients-list-page',
  templateUrl: './patients-list-page.component.html',
  standalone: true,
  imports: [MatTableModule, MatProgressSpinnerModule, MatButtonModule, MatIconModule],
})
export class PatientsListPageComponent {}
