"use strict";
var components_1 = require('./components');
var logged_in_guard_1 = require('./services/logged-in.guard');
var exercise_component_1 = require("./components/exercise.component");
exports.routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: components_1.LoginComponent },
    { path: 'Exercise', component: exercise_component_1.ExerciseComponent, canActivate: [logged_in_guard_1.LoggedInGuard] }
];
//# sourceMappingURL=app.routes.js.map