import { Component } from '@angular/core';
import './styles/app.css';

@Component({
    selector: 'app',
    template: require('./templates/app.component.html')
})
export class AppComponent {
    title="Hello world";
}