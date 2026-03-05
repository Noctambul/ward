import { HttpClient } from "@angular/common/http";
import { computed, inject, Injectable, signal } from "@angular/core";
import { environment } from "../../../environments/environment";
import { Observable, tap, map } from "rxjs";

type LoginResponse = { token: string }

const AUTH_URL = `${environment.apiV1}/auth/login`
const AUTH_TOKEN_KEY = 'access_token'

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private http = inject(HttpClient)

    private readonly token = signal(localStorage.getItem(AUTH_TOKEN_KEY))

    readonly isAuthenticated = computed(() => !!this.token())

    login(email: string, password: string) : Observable<void> {
      return this.http.post<LoginResponse>(AUTH_URL, { email, password }).pipe(
        tap(({token}) => {
          localStorage.setItem(AUTH_TOKEN_KEY, token)
          this.token.set(token)
        }),
        map(() => undefined)
      )
    }

    logout() {
      localStorage.removeItem(AUTH_TOKEN_KEY)
      this.token.set("")
    }
}
