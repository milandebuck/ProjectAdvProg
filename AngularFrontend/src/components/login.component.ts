// login.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

//services
import { UserService } from './../services/user.service';

//model
import { User } from './../models/User';

@Component({
    moduleId:module.id,
    selector: 'login',
    template: require('./templates/login.component.html'),
    styles: [ require('./styles/login.component.css') ]
})
export class LoginComponent {
    constructor(private userService: UserService, private router: Router) {}

    onSubmit(event,email, password) {
        event.preventDefault();
        console.log("submitting");
        this.userService.login(email, password).subscribe((result) => {
            if (result) {
                this.router.navigate(['Exercise']);
            }
        });
    }
}