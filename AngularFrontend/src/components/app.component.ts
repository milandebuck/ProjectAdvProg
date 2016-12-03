import { Component } from '@angular/core';
import './rxjs-operators';
import { LoggedInGuard } from '../services/logged-in.guard'

@Component({
    selector: 'app',
    template: require('./templates/app.component.html'),
    styles: [ require('./styles/app.component.css') ]
})
export class AppComponent {
    title="Hello world";
}