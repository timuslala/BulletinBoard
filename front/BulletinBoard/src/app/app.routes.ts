import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'ads',
  },
  {
    path: 'ads',
    loadComponent: () =>
      import('./pages/ads/ad-list-page/ad-list-page.component').then(
        (m) => m.AdListPageComponent
      ),
  },
];

