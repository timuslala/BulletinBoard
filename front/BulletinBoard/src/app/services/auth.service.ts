import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http
      .post<{ token: string }>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap((response) => {
          this.tokenService.setToken(response.token);
        })
      );
  }

  logout() {
    this.tokenService.removeToken();
  }

  getToken(): string | null {
    return this.tokenService.getToken();
  }
}
