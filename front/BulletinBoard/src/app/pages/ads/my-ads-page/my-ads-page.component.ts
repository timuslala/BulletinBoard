import { Component, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdCardComponent } from '../../../components/ads/ad-card/ad-card.component';
import { Ad } from '../../../models/ad.model';
import { AdService } from '../../../services/ad.service';

@Component({
  selector: 'app-my-ads-page',
  imports: [CommonModule, AdCardComponent],
  templateUrl: './my-ads-page.component.html',
  styleUrl: './my-ads-page.component.scss',
})
export class MyAdsPageComponent {
  private adService = inject(AdService);

  myAds = signal<Ad[]>([]);

  constructor() {
    effect(() => {
      this.adService.getMyAds().subscribe((data) => {
        this.myAds.set(data);
        console.log(data);
      });
    });
  }
}
