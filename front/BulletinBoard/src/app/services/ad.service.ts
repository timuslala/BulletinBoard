import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Ad, AdStatus } from '../models/ad.model';
import { TokenService } from './token.service';

@Injectable({ providedIn: 'root' })
export class AdService {
  private apiUrl = 'http://localhost:8080/api/ads';
  private tokenService = inject(TokenService);

  http = inject(HttpClient);

  private getAuthHeaders() {
    const token = this.tokenService.getToken();
    console.log(`Token ${token || 'BRAK TOKENA'}`);
    return {
      Authorization: `Bearer ${token || ''}`,
    };
  }

  getAllAds(): Observable<Ad[]> {
    const headers = this.getAuthHeaders();
    const params = new HttpParams().set('query', '').set('tag', '');

    return this.http.get<Ad[]>(`${this.apiUrl}/search`, { headers, params });
  }

  getAdById(id: number): Observable<Ad> {
    const headers = this.getAuthHeaders();
    return this.http.get<Ad>(`${this.apiUrl}/${id}`, { headers });
  }

  addAd(ad: Ad): Observable<Ad> {
    const headers = this.getAuthHeaders();

    const payload = {
      title: ad.title,
      description: ad.description,
      images: ad.images,
      tags: ad.tags,
      showEmail: ad.showEmail,
      showPhone: ad.showPhone,
    };

    return this.http.post<Ad>(this.apiUrl, payload, { headers });
  }
}
