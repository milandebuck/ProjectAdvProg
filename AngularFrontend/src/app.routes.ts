import { LoginComponent } from './components'

import { LoggedInGuard } from './services/logged-in.guard';
import {ExerciseComponent} from "./components/exercise.component";

export const routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'Exercise', component: ExerciseComponent, canActivate: [LoggedInGuard] }
];