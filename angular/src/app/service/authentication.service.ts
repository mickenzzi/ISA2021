import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginDetails } from '../model/login-details';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
   
    constructor(private http: HttpClient) { }
    private authUrl = 'http://localhost:8081/myApp/auth';
	
	public loginUser(loginDetails: LoginDetails) {
        return this.http.post(`${this.authUrl}/login`, loginDetails);
    }


}
