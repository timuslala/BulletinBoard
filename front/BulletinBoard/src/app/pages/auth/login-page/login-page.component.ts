import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from '../../../components/auth/login-form/login-form.component';
import { AuthService } from '../../../services/auth.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, LoginFormComponent, MatSnackBarModule],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  onSubmit(credentials: { username: string; password: string }) {
    this.authService.login(credentials).subscribe({
      next: () => {
        this.snackbar.open('Zalogowano pomyślnie!', 'Zamknij', { duration: 3000 });
        this.router.navigate(['/ads']);
      },
      error: () => {
        this.snackbar.open('Nieprawidłowe dane logowania.', 'Zamknij', { duration: 3000 });
      },
    });
  }
}
