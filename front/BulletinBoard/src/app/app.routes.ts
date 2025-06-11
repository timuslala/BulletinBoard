import { Routes } from '@angular/router';

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
    path: 'ads',
    loadComponent: () =>
      import('./pages/ads/ad-list-page/ad-list-page.component').then(
        (m) => m.AdListPageComponent
      ),
  },
  {
    path: 'ads/new',
    loadComponent: () =>
      import('./pages/ads/ad-form-page/ad-form-page.component').then(
        (m) => m.AdFormPageComponent
      ),
  },
  {
    path: 'ads/:id',
    loadComponent: () =>
      import('./pages/ads/ad-detail-page/ad-detail-page.component').then(
        (m) => m.AdDetailPageComponent
      ),
  },
  {
    path: 'inbox',
    loadComponent: () =>
      import('./pages/messages/inbox-page/inbox-page.component').then(
        (m) => m.InboxPageComponent
      ),
  },
  {
    path: 'inbox/conversation/:id',
    loadComponent: () =>
      import(
        './pages/messages/messages-conversation/messages-conversation.component'
      ).then((m) => m.MessagesConversationPageComponent),
  },
];
