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
  {
    path: 'ads/:id',
    loadComponent: () =>
      import('./pages/ads/ad-detail-page/ad-detail-page.component').then(
        (m) => m.AdDetailPageComponent
      ),
  },
];
