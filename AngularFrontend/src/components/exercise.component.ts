// exercise.component.ts
import { Component } from '@angular/core';
import { Http, Headers } from '@angular/http';

//model
import { Entry } from './../models/Entry';

@Component({
    selector: 'exercise',
    template: require('./templates/exercise.component.html'),
    styles: [ require('./styles/exercise.component.css') ]
})
export class ExerciseComponent {
    public entries = [];
    constructor() {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('',localStorage.getItem('auth-token'));

        Http.get('/login', {headers})
            .map(res => res.json())
            .map((res) => {
                if (res.success) {
                    localStorage.setItem('auth_token', res.auth_token);
                    this.loggedIn = true;
                }
                return res.success;
            });
    }
}