// user.service.ts
import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

@Injectable()
export class EntryService{

    constructor(private http: Http){};


    getEntries(count){
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('authorization',localStorage.getItem('auth-token'));
        return this.http
            .get('http://teammartini.heroku.com/Exercise', {headers})
            .map(this.extractData)
            .catch(this.handleError);
    }

    private extractData(res: Response) {
        let body = res.json();
        return body.data || { };
    }

    private handleError(error: Response | any){
        return error.msg
    }
}