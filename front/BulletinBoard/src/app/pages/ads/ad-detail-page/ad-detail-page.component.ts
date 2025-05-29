import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdService } from '../../../services/ad.service';
import { Ad } from '../../../models/ad.model';
import { AdDetailComponent } from '../../../components/ads/ad-detail/ad-detail.component';

@Component({
  selector: 'app-ad-detail-page',
  standalone: true,
  imports: [CommonModule, AdDetailComponent],
  templateUrl: './ad-detail-page.component.html',
  styleUrls: ['./ad-detail-page.component.scss'],
})
export class AdDetailPageComponent implements OnInit {
  ad: Ad | null = null;

  constructor(private route: ActivatedRoute, private adService: AdService) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.adService.getAdById(id).subscribe((ad) => (this.ad = ad));
  }
}
