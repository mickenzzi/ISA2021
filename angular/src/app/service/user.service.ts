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
  
  public getAllUsers() {
	  return this.http.get<User[]>(`${this.userUrl}/getAllUsers`);
  }
  
  public getUser(id: number) {
	  return this.http.get<User>(`${this.userUrl}/getUserById/${id}`);
  }
  
    public enableUser(username: string) {
	  return this.http.get<User>(`${this.userUrl}/enableUser/${username}`);
  }
  
   public disableUser(username: string) {
	  return this.http.get<User>(`${this.userUrl}/disableUser/${username}`);
  }
  
  public createUser(user: User) {
        return this.http.post(`${this.userUrl}/createUser`, user);
    }
  

}
