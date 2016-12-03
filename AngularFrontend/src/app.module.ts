import { NgModule } from '@angular/core';
import { HttpModule,JsonpModule } from '@angular/http';
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
import { EntryService } from './services/entry.service';


@NgModule({
    bootstrap: [ AppComponent ],
    declarations: [ AppComponent,LoginComponent,ExerciseComponent ],
    imports: [
        BrowserModule,
        HttpModule,
        FormsModule,
        RouterModule.forRoot(routes),
        JsonpModule
    ],
    providers: [
        UserService,
        LoggedInGuard,
        EntryService
    ]
})
export class AppModule {}