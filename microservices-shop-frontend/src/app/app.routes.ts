import { Routes } from '@angular/router';

import { HomePageComponent } from './pages/home-page/home-page.component';
import { AddAccountComponent } from './pages/add-account/add-account.component';

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent
  },
  {
    path: 'add-account',
    component: AddAccountComponent
  }
];
