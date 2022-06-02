import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { AuthenticationService } from './authentication.service';
import { Loyalty } from "../model/loyalty";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userUrl = 'http://localhost:8081/myApp/api/users';
  private financiesUrl = 'http://localhost:8081/myApp/api/financies';

  constructor(private http: HttpClient, private auth: AuthenticationService) {
  }

  public getAllUsers(id: number) {
    return this.http.get<User[]>(`${this.userUrl}/getAllUsers/${id}`);
  }

  public getUser(id: number) {
    return this.http.get<User>(`${this.userUrl}/getUserById/${id}`);
  }

  public deleteUser(id: number) {
    return this.http.get<User>(`${this.userUrl}/deleteUser/${id}`);
  }

  public enableUser(username: string, idRequest: number) {
    return this.http.get<User>(`${this.userUrl}/enableUser/${username}/${idRequest}`);
  }

  public disableUser(username: string, idRequest: number, rejectText: string) {
    return this.http.get<User>(`${this.userUrl}/disableUser/${username}/${idRequest}/${rejectText}`);
  }

  public approveDeleteRequest(username: string, idRequest: number, textRequest: string) {
    return this.http.get<User>(`${this.userUrl}/approveDeleteRequest/${username}/${idRequest}/${textRequest}`);
  }

  public rejectDeleteRequest(username: string, idRequest: number, textRequest: string) {
    return this.http.get<User>(`${this.userUrl}/rejectDeleteRequest/${username}/${idRequest}/${textRequest}`);
  }

  public createUser(user: User) {
    return this.http.post(`${this.userUrl}/createUser`, user);
  }

  public updateUser(user: User) {
    return this.http.post(`${this.userUrl}/updateUser`, user);
  }

  public findUser(username?: string) {
    return this.http.get<User>(`${this.userUrl}/whoAmI/${username}`);
  }

  public getInstructorProfit(id: number, start: string, end: string) {
    return this.http.get<string>(`${this.financiesUrl}/getInstructorProfit/${id}/${start}/${end}`);
  }

  public getYearProfit(year: string) {
    return this.http.get<string>(`${this.financiesUrl}/getYearProfit/${year}`);
  }

  public getYearPerMonthProfit(year: string) {
    return this.http.get<number[]>(`${this.financiesUrl}/getYearPerMonthProfit/${year}`);
  }

  public getReservationsPerMonth(id: number) {
    return this.http.get<number[]>(`${this.financiesUrl}/getReservationsPerMonth/${id}`);
  }
  public getReservationsPerWeek(id: number) {
    return this.http.get<number[]>(`${this.financiesUrl}/getReservationsPerWeek/${id}`);
  }
  public getReservationsPerDay(id: number) {
    return this.http.get<number[]>(`${this.financiesUrl}/getReservationsPerDay/${id}`);
  }

  public getPercent() {
    return this.http.get<number>(`${this.financiesUrl}/getPercent`);
  }

  public editPercent(percent: string) {
    return this.http.post(`${this.financiesUrl}/editPercent`, percent);
  }

  public getGold() {
    return this.http.get<Loyalty>(`${this.userUrl}/findGold`)
  }

  public getSilver() {
    return this.http.get<Loyalty>(`${this.userUrl}/findSilver`)
  }

  public getBronze() {
    return this.http.get<Loyalty>(`${this.userUrl}/findBronze`)
  }

  public updateLoyalty(name: string, points: number) {
    return this.http.get<Loyalty>(`${this.userUrl}/updateLoyalty/${name}/${points}`)
  }
}



