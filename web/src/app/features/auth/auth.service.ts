import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
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

    login(email: string, password: string) : Observable<void> {
      console.log("Login", email, password, AUTH_URL)
      return this.http.post<LoginResponse>(AUTH_URL, { email, password }).pipe(
        tap(({token}) => {
          localStorage.setItem(AUTH_TOKEN_KEY, token)
          console.log("Token set", token)
        }),
        map(() => undefined)
      )
    }

    logout() {
      localStorage.removeItem(AUTH_TOKEN_KEY)
    }

    getToken() {
      return localStorage.getItem(AUTH_TOKEN_KEY)
    }

    isAuthenticated() {
      return !!this.getToken()
    }
}
