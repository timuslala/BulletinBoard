import { Component, inject, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdDetailComponent } from '../../../components/ads/ad-detail/ad-detail.component';
import { AdService } from '../../../services/ad.service';
import { Ad } from '../../../models/ad.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ad-detail-page',
  standalone: true,
  imports: [CommonModule, AdDetailComponent],
  templateUrl: './ad-detail-page.component.html',
  styleUrls: ['./ad-detail-page.component.scss'],
})
export class AdDetailPageComponent {
  private route = inject(ActivatedRoute);
  private adService = inject(AdService);
  ad = signal<Ad | null>(null);

  constructor() {
    effect(() => {
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.adService.getAdById(id).subscribe(data => {
        this.ad.set(data);
      });
    });
  }
}
