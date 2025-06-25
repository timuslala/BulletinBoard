import { Component, inject, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { AdService } from '../../../services/ad.service';
import { Ad } from '../../../models/ad.model';
import { AdEditFormComponent } from '../../../components/ads/ad-edit-form/ad-edit-form.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-ad-edit-page',
  standalone: true,
  imports: [CommonModule, AdEditFormComponent, MatSnackBarModule],
  templateUrl: './ad-edit-page.component.html',
  styleUrls: ['./ad-edit-page.component.scss'],
})
export class AdEditPageComponent {
  private route = inject(ActivatedRoute);
  private adService = inject(AdService);
  private router = inject(Router);
  private snackbar = inject(MatSnackBar);

  ad = signal<Ad | null>(null);

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.adService.getAdById(id).subscribe(ad => this.ad.set(ad));
  }

  onSubmit(updatedAd: Partial<Ad>) {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.adService.updateAd(id, updatedAd).subscribe(() => {
      this.snackbar.open('Ogłoszenie zostało zaktualizowane', 'Zamknij', { duration: 3000 });
      this.router.navigate(['/ads', id]);
    });
  }
}
