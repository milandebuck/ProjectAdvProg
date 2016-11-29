import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule }   from '@angular/router';
import { FormsModule } from '@angular/forms';

import {
    AppComponent,
    LoginComponent,
    ExerciseComponent
} from './components';

import { routes } from './app.routes';

//services

import { UserService } from './services/user.service';
import { LoggedInGuard } from './services/logged-in.guard';


@NgModule({
    bootstrap: [ AppComponent ],
    declarations: [ AppComponent,LoginComponent,ExerciseComponent ],
    imports: [
        BrowserModule,
        HttpModule,
        FormsModule,
        RouterModule.forRoot(routes)
    ],
    providers: [ UserService,LoggedInGuard ]
})
export class AppModule {}