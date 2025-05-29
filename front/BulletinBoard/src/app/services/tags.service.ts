import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class TagsService {
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
