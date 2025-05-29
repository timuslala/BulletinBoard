import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdCardComponent } from '../../../components/ads/ad-card/ad-card.component';
import { Ad } from '../../../models/ad.model';

@Component({
  selector: 'app-ad-list-page',
  standalone: true,
  imports: [CommonModule, AdCardComponent],
  templateUrl: './ad-list-page.component.html',
  styleUrls: ['./ad-list-page.component.scss'],
})
export class AdListPageComponent {
  ads: Ad[] = [
    {
      id: 1,
      title: 'Sprzedam rower',
      description: 'Prawie nowy, mało używany rower górski.',
      tags: ['rower', 'sport'],
      showEmail: true,
      showPhone: false,
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Left_side_of_Flying_Pigeon.jpg/1280px-Left_side_of_Flying_Pigeon.jpg',
      status: 'PUBLISHED',
    },
    {
      id: 2,
      title: 'Oddam książki',
      description: 'Darmowe książki do oddania, literatura piękna.',
      tags: ['książki', 'darmowe'],
      showEmail: false,
      showPhone: true,
      imageUrl: 'https://ecsmedia.pl/cdn-cgi/image/format=webp,/c/pakiet-wiedzmin-tom-1-8-b-iext139878790.jpg',
      status: 'PUBLISHED',
    },
  ];
}
