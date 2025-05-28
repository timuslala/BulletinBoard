import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadComponent: () => {
      // @TODO: Change to homePage
      return import('./components/message/message.component').then(
        (m) => m.MessageComponent
      );
    },
  },
];
