import { LoginComponent } from './components'

import { LoggedInGuard } from './services/logged-in.guard';

export const routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    //{ path: 'profile', component: ProfileComponent, canActivate: [LoggedInGuard] }
];