import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule }   from '@angular/router';

import {
    AppComponent,
    LoginComponent
} from './components';

import { routes } from './app.routes';

//services

import { UserService } from './services/user.service';
import { LoggedInGuard } from './services/logged-in.guard';


@NgModule({
    bootstrap: [ AppComponent ],
    declarations: [ AppComponent,LoginComponent ],
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule.forRoot(routes)
    ],
    providers: [ UserService,LoggedInGuard ]
})
export class AppModule {}