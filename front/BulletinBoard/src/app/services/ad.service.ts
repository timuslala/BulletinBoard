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

  private mockAds: Ad[] = [
    {
      id: 1,
      title: 'Sprzedam rower',
      description: 'Prawie nowy rower miejski.',
      tags: ['sport', 'rower'],
      showEmail: true,
      showPhone: false,
      imageUrl:
        'https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Left_side_of_Flying_Pigeon.jpg/1280px-Left_side_of_Flying_Pigeon.jpg',
      status: AdStatus.PUBLISHED,
    },
    {
      id: 2,
      title: 'Oddam książki',
      description: 'Dużo książek do oddania za darmo.',
      tags: ['książki', 'darmowe'],
      showEmail: false,
      showPhone: true,
      imageUrl:
        'https://ecsmedia.pl/cdn-cgi/image/format=webp,/c/pakiet-wiedzmin-tom-1-8-b-iext139878790.jpg',
      status: AdStatus.PUBLISHED,
    },
  ];

  private getAuthHeaders() {
    const token = this.tokenService.getToken();
    console.log(`Token ${token || "BRAK TOKENA"}`);
    return {
      Authorization: `Bearer ${token || ''}`,
    };
  }

  getAllAds(): Observable<Ad[]> {
    const headers = this.getAuthHeaders();
    const params = new HttpParams().set('query', '').set('tag', '');

    // const test = this.http.get<Ad[]>(`${this.apiUrl}/my`, {
    //   headers,
    //   params,
    // });
    // test.subscribe((data) => {
    //   console.log(data);
    //   this.mockAds = data;
    // });

    return this.http.get<Ad[]>(`${this.apiUrl}/search`, { headers, params });
  }

  getAdById(id: number): Observable<Ad> {
    const headers = this.getAuthHeaders();
    return this.http.get<Ad>(`${this.apiUrl}/${id}`, { headers });
  }

  addAd(ad: Ad): Observable<Ad> {
    const newAd = {
      ...ad,
      id: this.mockAds.length + 1,
      status: AdStatus.PUBLISHED,
    };
    this.mockAds.push(newAd);
    return of(newAd);
  }

  createAd(ad: Ad, photos: File[]): Observable<Ad> {
    const formData = new FormData();
    formData.append(
      'ad',
      new Blob([JSON.stringify(ad)], { type: 'application/json' })
    );
    photos.forEach((photo) => formData.append('photos', photo));
    return this.http.post<Ad>(this.apiUrl, formData);
  }
}
