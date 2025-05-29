import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { Ad } from '../../../models/ad.model';

@Component({
  selector: 'app-ad-card',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './ad-card.component.html',
  styleUrls: ['./ad-card.component.scss'],
})
export class AdCardComponent {
  @Input() ad!: Ad;
}
