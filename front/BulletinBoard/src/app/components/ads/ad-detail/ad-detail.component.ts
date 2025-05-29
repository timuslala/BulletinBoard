import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ad } from '../../../models/ad.model';

@Component({
  selector: 'app-ad-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ad-detail.component.html',
  styleUrls: ['./ad-detail.component.scss'],
})
export class AdDetailComponent {
  @Input() ad: Ad | null = null;

  onImageClick() {
    console.log('Kliknięto zdjęcie (galeria mogłaby się tu pojawić)');
  }
}
