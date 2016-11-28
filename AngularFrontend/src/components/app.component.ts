import { Component } from '@angular/core';

@Component({
    selector: 'app',
    template: require('./templates/app.component.html'),
    styles: [ require('./styles/app.component.css') ]
})
export class AppComponent {
    title="Hello world";
}