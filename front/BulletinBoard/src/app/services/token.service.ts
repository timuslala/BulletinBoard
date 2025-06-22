import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly tokenKey = 'auth_token';

  setToken(token: string) {
    if (typeof window !== 'undefined') {
      sessionStorage.setItem(this.tokenKey, token);
    }
  }

  getToken(): string | null {
    if (typeof window !== 'undefined') {
      return sessionStorage.getItem(this.tokenKey);
    }
    return null;
  }

  removeToken() {
    if (typeof window !== 'undefined') {
      sessionStorage.removeItem(this.tokenKey);
    }
  }

  getJti(): number | null {
    const token = this.getToken();
    if (!token) return null;

    const parts = token.split('.');
    if (parts.length !== 3) return null;

    try {
      const payload = JSON.parse(atob(parts[1]));
      return payload.jti || null;
    } catch (e) {
      console.error('Invalid token format or base64 decode error', e);
      return null;
    }
  }

  getAuthHeaders() {
    const token = this.getToken();

    const jti = this.getJti();

    console.log(`Token ${token || 'BRAK TOKENA'}`);

    return {
      Authorization: `Bearer ${token || ''}`,
    };
  }
}
