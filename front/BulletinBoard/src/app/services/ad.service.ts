import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from '../models/ad';

@Injectable({ providedIn: 'root' })
export class AdService {
  private apiUrl = '/api/ads';

  constructor(private http: HttpClient) {}

  createAd(ad: Ad, photos: File[]): Observable<Ad> {
    const formData = new FormData();
    formData.append('ad', new Blob([JSON.stringify(ad)], { type: 'application/json' }));
    photos.forEach(photo => formData.append('photos', photo));
    return this.http.post<Ad>(this.apiUrl, formData);
  }

  searchAds(query: string = '', tag: string = '', page = 0, size = 10): Observable<any> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);
    if (query) params = params.set('query', query);
    if (tag) params = params.set('tag', tag);
    return this.http.get<any>(`${this.apiUrl}/search`, { params });
  }
}