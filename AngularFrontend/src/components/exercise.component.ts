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
    public count = 0;
    public lenght = 10;
    private answers = [];

    constructor( private entryService : EntryService){};

    entries=this.entryService.getEntries(this.lenght);

    next(answer){
        this.answers.push(answer);
        if(this.count > this.lenght)this.count++;
        else{
            this.correctExercise().subscribe((res) => {
               if(res){

               }
            });
        }
    }

    private correctExercise(){
        
    }
}