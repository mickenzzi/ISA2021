import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { catchError, map } from 'rxjs/operators';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
  private http: HttpClient,
  private auth: AuthenticationService
  ) { }
  private userUrl = 'http://localhost:8081/myApp/api/users';
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser')); 
  reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.currentUser.token);
  
  public getAllUsers(id: number) {
	return this.http.get<User[]>(`${this.userUrl}/getAllUsers/${id}`, {headers: this.reqHeader});
  }
  
  public getUser(id: number) {
	return this.http.get<User>(`${this.userUrl}/getUserById/${id}`, {headers: this.reqHeader});
  }
  
  public deleteUser(id: number){
	return this.http.get<User>(`${this.userUrl}/deleteUser/${id}`, {headers: this.reqHeader});
  }

  public enableUser(username: string, idRequest: number) {
	return this.http.get<User>(`${this.userUrl}/enableUser/${username}/${idRequest}`, {headers: this.reqHeader});
  }
  
   public disableUser(username: string, idRequest: number, rejectText: string) {
	return this.http.get<User>(`${this.userUrl}/disableUser/${username}/${idRequest}/${rejectText}`, {headers: this.reqHeader});
  }
  
  public approveDeleteRequest(username: string, idRequest: number, textRequest: string) {
    return this.http.get<User>(`${this.userUrl}/approveDeleteRequest/${username}/${idRequest}/${textRequest}`, {headers: this.reqHeader});
  }
  
  public rejectDeleteRequest(username: string, idRequest: number, textRequest: string) {
	return this.http.get<User>(`${this.userUrl}/rejectDeleteRequest/${username}/${idRequest}/${textRequest}`, {headers: this.reqHeader});
  }
  
  public createUser(user: User) {
	return this.http.post(`${this.userUrl}/createUser`, user);
  }
	
  public updateUser(user: User){
	return this.http.post(`${this.userUrl}/updateUser`, user, {headers: this.reqHeader});
  }
  
  public findUser(username: string){
	return this.http.get<User>(`${this.userUrl}/whoAmI/${username}`, {headers: this.reqHeader});
  }
   

}
