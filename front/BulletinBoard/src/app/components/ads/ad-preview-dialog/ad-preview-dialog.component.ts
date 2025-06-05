import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { AdDetailComponent } from '../ad-detail/ad-detail.component';
import { Ad, AdStatus } from '../../../models/ad.model';

@Component({
  selector: 'app-ad-preview-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, AdDetailComponent],
  templateUrl: './ad-preview-dialog.component.html',
  styleUrls: ['./ad-preview-dialog.component.scss'],
})
export class AdPreviewDialogComponent {
  ad: Ad;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.ad = {
      id: 0,
      title: data.title || 'Brak tytułu',
      description: data.description || 'Brak opisu',
      tags: data.tags || [],
      imageUrl: data.imageUrl || '',
      showEmail: data.showEmail || false,
      showPhone: data.showPhone || false,
      status: AdStatus.DRAFT,
    };
  }
}
