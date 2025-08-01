import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ad, AdStatus } from '../../../models/ad.model';
import { TokenService } from '../../../services/token.service';
import { AdService } from '../../../services/ad.service';
import { MessageService } from '../../../services/messages.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ad-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ad-detail.component.html',
  styleUrls: ['./ad-detail.component.scss'],
})
export class AdDetailComponent implements OnInit {
  @Input() ad: Ad | null = null;

  userIdFromToken: number | null = null;
  selectedImageIndex = 0;

  constructor(
    private tokenService: TokenService,
    private adService: AdService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userIdFromToken = this.tokenService.getJti();
  }

  isMyAd(): boolean {
    return this.ad?.sellerId == this.userIdFromToken;
  }

  isPublished(): boolean {
    return this.ad?.status === 'PUBLISHED';
  }

  selectImage(index: number): void {
    this.selectedImageIndex = index;
  }

  get images(): string[] {
    return this.ad?.images ?? [];
  }

  get hasImages(): boolean {
    return this.images.length > 0;
  }

  get selectedImage(): string | null {
    return this.hasImages ? this.images[this.selectedImageIndex] : null;
  }

  onEditAd() {
    if (this.ad) {
      this.router.navigate(['/ads', this.ad.id, 'edit']);
    }
  }

  onDeleteAd() {
    if (!this.ad) return;
    if (confirm('Czy na pewno chcesz usunąć to ogłoszenie?')) {
      this.adService.deleteAd(this.ad.id).subscribe(() => {
        alert('Ogłoszenie zostało usunięte.');
        this.router.navigate(['/ads/my']);
      });
    }
  }

  onSendMessage() {
    if (this.ad) {
      this.messageService.sendMessageToSeller(this.ad.id).subscribe();
    }
  }

  onToggleAdStatus() {
    if (!this.ad) return;

    const newStatus: AdStatus = this.isPublished()
      ? AdStatus.DRAFT
      : AdStatus.PUBLISHED;

    this.adService
      .updateAdStatus(this.ad.id, newStatus)
      .subscribe((updatedAd) => {
        this.ad!.status = updatedAd.status;
      });
  }
}
