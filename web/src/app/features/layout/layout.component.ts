
import { Component, inject } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";
import { MatToolbarModule } from "@angular/material/toolbar";
import { Router, RouterOutlet } from "@angular/router";
import { AuthService } from "../auth/auth.service";

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, MatToolbarModule, MatButtonModule, CommonModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
  private router: Router = inject(Router)
  private authService: AuthService = inject(AuthService)

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get isAuthenticated() {
    return this.authService.isAuthenticated();
  }
}
