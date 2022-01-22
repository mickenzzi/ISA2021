import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginDetails } from '../model/login-details';
import { HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { of } from 'rxjs/internal/observable/of';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { UserTokenState } from '../model/user-token-state';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
   
    constructor(private http: HttpClient) { }
    private authUrl = 'http://localhost:8081/myApp/auth';
	private access_token: any;
	
	
	public loginUser(loginDetails: LoginDetails): Observable<void>{		
		const body = {
			username: loginDetails.username,
			password: loginDetails.password
		}
        return this.http.post<UserTokenState>(`${this.authUrl}/login`, body)
		.pipe(map((res)=>{
			const username1 = body.username;
			this.access_token = res.accessToken;
			const token = this.access_token;
			localStorage.setItem('currentUser', JSON.stringify({username1,token}));
		}));
    }
	
	getToken(): string{
		//@ts-ignore
		const currentUser =  JSON.parse(localStorage.getItem('currentUser'));
		console.log(currentUser);
		const token = currentUser && currentUser.token;
		return token? token: '';
	}
	

}
