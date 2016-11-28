var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// login.component.ts
var core_1 = require('@angular/core');
var router_1 = require('@angular/router');
//services
var user_service_1 = require('./../services/user.service');
var LoginComponent = (function () {
    function LoginComponent(userService, router) {
        this.userService = userService;
        this.router = router;
    }
    LoginComponent.prototype.onSubmit = function (email, password) {
        var _this = this;
        this.userService.login(email, password).subscribe(function (result) {
            if (result) {
                _this.router.navigate(['']);
            }
        });
    };
    LoginComponent = __decorate([
        core_1.Component({
            selector: 'login',
            template: require('./templates/login.component.html'),
            styles: [require('./styles/login.component.css')]
        }), 
        __metadata('design:paramtypes', [user_service_1.UserService, (typeof (_a = typeof router_1.Router !== 'undefined' && router_1.Router) === 'function' && _a) || Object])
    ], LoginComponent);
    return LoginComponent;
    var _a;
})();
exports.LoginComponent = LoginComponent;
//# sourceMappingURL=login.component.js.map