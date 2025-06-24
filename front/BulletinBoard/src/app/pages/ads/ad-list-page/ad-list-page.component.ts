import { Component, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdCardComponent } from '../../../components/ads/ad-card/ad-card.component';
import { SearchBarComponent } from '../../../components/ads/search-bar/search-bar.component';
import { Ad } from '../../../models/ad.model';
import { AdService } from '../../../services/ad.service';

@Component({
  selector: 'app-ad-list-page',
  standalone: true,
  imports: [CommonModule, AdCardComponent, SearchBarComponent],
  templateUrl: './ad-list-page.component.html',
  styleUrls: ['./ad-list-page.component.scss'],
})
export class AdListPageComponent {
  private adService = inject(AdService);

  ads = signal<Ad[]>([]);

  constructor() {
    effect(() => {
      this.adService.getAllAds().subscribe((data) => {
        this.ads.set(data);
        console.log(data);
      });
    });
  }

  searchAds(query: string, tags: string[]) {
    this.adService.searchAds(query, tags).subscribe((data) => {
      this.ads.set(data);
    });
  }
}
