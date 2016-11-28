// user.service.ts
import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

@Injectable()
export class EntryService{

    constructor(private http: Http){}
    private headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('',localStorage.getItem('auth-token'));
    getEntries(count){


    }

    private extractData(res: Response) {
        let body = res.json();
        return body.data || { };
    }
}