import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
	
  constructor(private http: HttpClient) { }
  private userUrl = 'http://localhost:8081/myApp/api/users';
  
  public getAllUsers(id: number) {
	return this.http.get<User[]>(`${this.userUrl}/getAllUsers/${id}`);
  }
  
  public getUser(id: number) {
	return this.http.get<User>(`${this.userUrl}/getUserById/${id}`);
  }
  
  public deleteUser(id: number){
	return this.http.get<User>(`${this.userUrl}/deleteUser/${id}`);
  }

  public enableUser(username: string, idRequest: number) {
	return this.http.get<User>(`${this.userUrl}/enableUser/${username}/${idRequest}`);
  }
  
   public disableUser(username: string, idRequest: number) {
	return this.http.get<User>(`${this.userUrl}/disableUser/${username}/${idRequest}`);
  }
  
  public approveDeleteRequest(username: string, idRequest: number) {
    return this.http.get<User>(`${this.userUrl}/approveDeleteRequest/${username}/${idRequest}`);
  }
  
  public rejectDeleteRequest(username: string, idRequest: number) {
	return this.http.get<User>(`${this.userUrl}/rejectDeleteRequest/${username}/${idRequest}`);
  }
  
  public createUser(user: User) {
	return this.http.post(`${this.userUrl}/createUser`, user);
  }
	
  public updateUser(user: User){
	return this.http.post(`${this.userUrl}/updateUser`, user);
  }
  

}
