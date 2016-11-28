// login.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from './../services/user.service';

@Component({
    selector: 'login',
    template: require('./templates/login.component.html')
})
export class LoginComponent {
    constructor(private userService: UserService, private router: Router) {}

    onSubmit(email, password) {
        this.userService.login(email, password).subscribe((result) => {
            if (result) {
                this.router.navigate(['']);
            }
        });
    }
}