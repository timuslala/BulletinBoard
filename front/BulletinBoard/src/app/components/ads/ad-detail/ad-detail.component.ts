import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ad } from '../../../models/ad.model';
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

  constructor(
    private tokenService: TokenService,
    private adService: AdService,
    private messageService: MessageService,
    private router: Router
  ) {}

  onImageClick() {
    console.log('Kliknięto zdjęcie (galeria mogłaby się tu pojawić)');
  }

  ngOnInit(): void {
    this.userIdFromToken = this.tokenService.getJti();
    console.log(this.isMyAd());
    console.log(this.isPublished());
  }

  isMyAd(): boolean {
    return this.ad?.sellerId == this.userIdFromToken;
  }

  isPublished(): boolean {
    return this.ad?.status === 'PUBLISHED';
  }

  onEditAd() {
    console.log('Edytuj ogłoszenie:', this.ad);
    this.ad && this.adService.editAd(this.ad.id).subscribe();
  }

  onDeleteAd() {
    if (confirm('Czy na pewno chcesz usunąć to ogłoszenie?')) {
      this.ad &&
        this.adService.deleteAd(this.ad.id).subscribe(() => {
          alert('Ogłoszenie zostało usunięte.');
          this.router.navigate(['/ads/my']);
        });
    }
  }

  onSendMessage() {
    console.log('Wysyłanie wiadomości do właściciela ogłoszenia:', this.ad);
    this.ad && this.messageService.sendMessageToSeller(this.ad.id).subscribe();
  }
}
