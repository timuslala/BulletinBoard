import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Ad } from '../models/ad.model';

@Injectable({ providedIn: 'root' })
export class AdService {
  private apiUrl = '/api/ads';

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
      status: 'PUBLISHED',
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
      status: 'PUBLISHED',
    },
  ];

  getAllAds(): Observable<Ad[]> {
    return of(this.mockAds); // można później łatwo zastąpić this.http.get(...)
  }

  getAdById(id: number) {
    const ad = this.mockAds.find((ad) => ad.id === id);
    return of(ad || null); // używamy of() żeby zasymulować Observable
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

  searchAds(
    query: string = '',
    tag: string = '',
    page = 0,
    size = 10
  ): Observable<any> {
    // @TODO: Observable<Ad>
    let params = new HttpParams().set('page', page).set('size', size);
    if (query) params = params.set('query', query);
    if (tag) params = params.set('tag', tag);
    return this.http.get<any>(`${this.apiUrl}/search`, { params });
  }
}
