import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdFormComponent } from '../../../components/ads/ad-form/ad-form.component';
import { AdService } from '../../../services/ad.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-ad-form-page',
  standalone: true,
  imports: [CommonModule, AdFormComponent, MatSnackBarModule],
  templateUrl: './ad-form-page.component.html',
  styleUrls: ['./ad-form-page.component.scss'],
})
export class AdFormPageComponent {
  constructor(
    private adService: AdService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  onSubmit(ad: any) {
    this.adService.addAd(ad).subscribe(() => {
      this.snackbar.open('Ogłoszenie zostało dodane!', 'Zamknij', {
        duration: 3000,
      });
      this.router.navigate(['/ads']);
    });
  }
}
