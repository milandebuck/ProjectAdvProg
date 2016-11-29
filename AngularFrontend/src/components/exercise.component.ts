// exercise.component.ts
import { Component } from '@angular/core';


//model
import { Entry } from './../models/Entry';

//service
import  { EntryService } from  './../services/entry.service';

@Component({
    selector: 'exercise',
    template: require('./templates/exercise.component.html'),
    styles: [ require('./styles/exercise.component.css') ]
})
export class ExerciseComponent {
    constructor( private entryService : EntryService){};

    entries=this.entryService.getEntries(10);
}