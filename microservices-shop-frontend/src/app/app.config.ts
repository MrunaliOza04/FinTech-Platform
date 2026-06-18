import { ApplicationConfig } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideAuth, authInterceptor } from 'angular-auth-oidc-client';

// 1. FIXED PATH: Pointing directly inside the config subfolder
import { authConfig } from './config/auth.config';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      // 2. FIXED INTERCEPTOR: Added () to execute the library function
      withInterceptors([authInterceptor()])
    ),
    provideAuth(authConfig)
  ]
};
