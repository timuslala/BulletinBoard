import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TagUsage } from '../models/tag.model';
import { TokenService } from './token.service';

@Injectable({ providedIn: 'root' })
export class TagsService {
  private apiUrl = 'http://localhost:8080/api/tags';

  private tokenService = inject(TokenService);
  private http = inject(HttpClient);

  suggestTags(prefix: string): Observable<TagUsage[]> {
    const headers = this.tokenService.getAuthHeaders();
    const params = new HttpParams().set('prefix', prefix);

    return this.http.get<TagUsage[]>(`${this.apiUrl}/suggest`, {
      headers,
      params,
    });
  }

  private mockTags: string[] = ['sport', 'książki', 'rower'];

  getTags(): string[] {
    return [...this.mockTags];
  }

  addTag(tag: string) {
    if (!this.mockTags.includes(tag)) {
      this.mockTags.push(tag);
    }
  }
}
