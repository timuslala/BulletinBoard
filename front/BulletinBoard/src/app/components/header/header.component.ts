import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  @Input() title!: string;

  constructor(private tokenService: TokenService, private router: Router) {}

  isLoggedIn(): boolean {
    return !!this.tokenService.getToken();
  }

  logout() {
    this.tokenService.removeToken();
    this.router.navigate(['/login']);
  }
}
