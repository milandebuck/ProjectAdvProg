// exercise.component.ts
import {Component, OnInit} from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

import { EntryService } from './../services/entry.service';

@Component({
    selector: 'exercise',
    template: require('./templates/exercise.component.html'),
    styles: [ require('./styles/exercise.component.css') ],
})
export class ExerciseComponent implements OnInit{
    entries;
    private error;
    constructor( private entryService:EntryService){

    };
    private lenght = 10;
    count = 0;
    private answers = [];
    ngOnInit(){
        console.log('initializing..');
        this.entries=this.getEntries(this.lenght);
    }

    getEntries(count){
        this.entryService.getEntries()
            .subscribe(
                entries => this.entries=entries,
                error => this.error = error
            )
    }

    private correctExercise(){

    }
}