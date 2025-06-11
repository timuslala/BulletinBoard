import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterFormComponent } from '../../../components/auth/register-form/register-form.component';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [CommonModule, RegisterFormComponent, MatSnackBarModule],
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss'],
})
export class RegisterPageComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  onSubmit(userData: any) {
    this.authService.register(userData).subscribe({
      next: () => {
        this.snackbar.open('Rejestracja zakończona sukcesem!', 'Zamknij', {
          duration: 3000,
        });
        this.router.navigate(['/login']);
      },
      error: () => {
        this.snackbar.open('Błąd rejestracji. Spróbuj ponownie.', 'Zamknij', {
          duration: 3000,
        });
      },
    });
  }
}
