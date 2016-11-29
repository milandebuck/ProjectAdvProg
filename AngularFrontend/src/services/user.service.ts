// user.service.ts
import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Injectable()
export class UserService {
    private loggedIn = false;

    constructor(private http: Http) {
        this.loggedIn = !!localStorage.getItem('auth_token');
    }

    login(username, password) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        return this.http
            .post('http://localhost:8080/login', JSON.stringify({username, password}), {headers})
            .map(res => res.json())
            .map((res) => {
                if (!res.status) {
                    localStorage.setItem('auth_token', res.token);
                    this.loggedIn = true;
                    return true
                }
                return false;
            });
    }

    logout() {
        localStorage.removeItem('auth_token');
        this.loggedIn = false;
    }

    isLoggedIn() {
        return this.loggedIn;
    }
}