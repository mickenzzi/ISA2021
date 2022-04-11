import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/user';
import {AuthenticationService} from './authentication.service';
import {Loyalty} from "../model/loyalty";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userUrl = 'http://localhost:8081/myApp/api/users';
  private financiesUrl = 'http://localhost:8081/myApp/api/financies';
  private token = this.auth.getToken();
  private reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.token);

  constructor(private http: HttpClient, private auth: AuthenticationService) {
    this.token = this.auth.getToken();
    this.reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.token);
  }

  public getAllUsers(id: number) {
    return this.http.get<User[]>(`${this.userUrl}/getAllUsers/${id}`, {headers: this.reqHeader});
  }

  public getUser(id: number) {
    return this.http.get<User>(`${this.userUrl}/getUserById/${id}`, {headers: this.reqHeader});
  }

  public deleteUser(id: number) {
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

  public updateUser(user: User) {
    return this.http.post(`${this.userUrl}/updateUser`, user, {headers: this.reqHeader});
  }

  public findUser(username?: string) {
    return this.http.get<User>(`${this.userUrl}/whoAmI/${username}`, {headers: this.reqHeader});
  }

  public getYearProfit(year: string) {
    return this.http.get<string>(`${this.financiesUrl}/getYearProfit/${year}`, {headers: this.reqHeader});
  }

  public getYearPerMonthProfit(year: string) {
    return this.http.get<number[]>(`${this.financiesUrl}/getYearPerMonthProfit/${year}`, {headers: this.reqHeader});
  }

  public getPercent() {
    return this.http.get<number>(`${this.financiesUrl}/getPercent`, {headers: this.reqHeader});
  }

  public editPercent(percent: string) {
    return this.http.post(`${this.financiesUrl}/editPercent`, percent, {headers: this.reqHeader});
  }

  public getGold() {
    return this.http.get<Loyalty>(`${this.userUrl}/findGold`, {headers: this.reqHeader})
  }

  public getSilver() {
    return this.http.get<Loyalty>(`${this.userUrl}/findSilver`, {headers: this.reqHeader})
  }

  public getBronze() {
    return this.http.get<Loyalty>(`${this.userUrl}/findBronze`, {headers: this.reqHeader})
  }

  public updateLoyalty(name: string, points: number) {
    return this.http.get<Loyalty>(`${this.userUrl}/updateLoyalty/${name}/${points}`, {headers: this.reqHeader})
  }
}



