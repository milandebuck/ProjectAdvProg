var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// exercise.component.ts
var core_1 = require('@angular/core');
//service
var entry_service_1 = require('./../services/entry.service');
var ExerciseComponent = (function () {
    function ExerciseComponent(entryService) {
        this.entryService = entryService;
        this.count = 0;
        this.lenght = 10;
        this.answers = [];
        this.entries = this.entryService.getEntries(this.lenght);
    }
    ;
    ExerciseComponent.prototype.next = function (answer) {
        this.answers.push(answer);
        if (this.count > this.lenght)
            this.count++;
        else {
            this.correctExercise().subscribe(function (res) {
                if (res) {
                }
            });
        }
    };
    ExerciseComponent.prototype.correctExercise = function () {
    };
    ExerciseComponent = __decorate([
        core_1.Component({
            selector: 'exercise',
            template: require('./templates/exercise.component.html'),
            styles: [require('./styles/exercise.component.css')]
        }), 
        __metadata('design:paramtypes', [entry_service_1.EntryService])
    ], ExerciseComponent);
    return ExerciseComponent;
})();
exports.ExerciseComponent = ExerciseComponent;
//# sourceMappingURL=exercise.component.js.map