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

  getAllAds(): Observable<Ad[]> {
    const headers = this.tokenService.getAuthHeaders();
    const params = new HttpParams().set('query', '').set('tag', '');

    return this.http.get<Ad[]>(`${this.apiUrl}/search`, { headers, params });
  }

  getMyAds(): Observable<Ad[]> {
    const headers = this.tokenService.getAuthHeaders();

    return this.http.get<Ad[]>(`${this.apiUrl}/my`, { headers });
  }

  getAdById(id: number): Observable<Ad> {
    const headers = this.tokenService.getAuthHeaders();
    return this.http.get<Ad>(`${this.apiUrl}/${id}`, { headers });
  }

  addAd(ad: Ad): Observable<Ad> {
    const headers = this.tokenService.getAuthHeaders();

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

  editAd(id: number): Observable<any> {
    const headers = this.tokenService.getAuthHeaders();
    console.log(`MOCK editAd: ogłoszenie o ID ${id}`);
    return of({ status: 'edited', id });
  }

  // deleteAd(id: number): Observable<any> {
  //   const headers = this.tokenService.getAuthHeaders();
  //   return this.http.delete<Ad>(`${this.apiUrl}/${id}`, { headers });
  // }

  deleteAd(id: number): Observable<any> {
    const headers = this.tokenService.getAuthHeaders();
    return this.http.put<Ad>(
      `${this.apiUrl}/${id}/status`,
      { status: AdStatus.ARCHIVED },
      { headers }
    );
  }

  updateAdStatus(id: number, status: AdStatus): Observable<Ad> {
    const headers = this.tokenService.getAuthHeaders();
    return this.http.put<Ad>(
      `${this.apiUrl}/${id}/status`,
      { status },
      { headers }
    );
  }

  searchAds(query: string, tags: string[]): Observable<Ad[]> {
    const headers = this.tokenService.getAuthHeaders();
    let params = new HttpParams().set('query', query || '');
    tags.forEach((tag) => {
      params = params.append('tag', tag);
    });
    return this.http.get<Ad[]>(`${this.apiUrl}/search`, { headers, params });
  }
}
