import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'ads',
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./pages/auth/register-page/register-page.component').then(
        (m) => m.RegisterPageComponent
      ),
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/auth/login-page/login-page.component').then(
        (m) => m.LoginPageComponent
      ),
  },
  {
    path: 'ads',
    loadComponent: () =>
      import('./pages/ads/ad-list-page/ad-list-page.component').then(
        (m) => m.AdListPageComponent
      ),
  },
  {
    path: 'ads/my',
    loadComponent: () =>
      import('./pages/ads/my-ads-page/my-ads-page.component').then(
        (m) => m.MyAdsPageComponent
      ),
    canActivate: [authGuard],
  },
  {
    path: 'ads/new',
    loadComponent: () =>
      import('./pages/ads/ad-form-page/ad-form-page.component').then(
        (m) => m.AdFormPageComponent
      ),
    canActivate: [authGuard],
  },
  {
    path: 'ads/:id',
    loadComponent: () =>
      import('./pages/ads/ad-detail-page/ad-detail-page.component').then(
        (m) => m.AdDetailPageComponent
      ),
  },
  {
    path: 'ads/:id/edit',
    loadComponent: () =>
      import('./pages/ads/ad-edit-page/ad-edit-page.component').then(
        (m) => m.AdEditPageComponent
      ),
    canActivate: [authGuard],
  },
  {
    path: 'inbox',
    loadComponent: () =>
      import('./pages/messages/inbox-page/inbox-page.component').then(
        (m) => m.InboxPageComponent
      ),
    canActivate: [authGuard],
  },
  {
    path: 'inbox/conversation/:id',
    loadComponent: () =>
      import(
        './pages/messages/messages-conversation/messages-conversation.component'
      ).then((m) => m.MessagesConversationPageComponent),
    canActivate: [authGuard],
  },
];
