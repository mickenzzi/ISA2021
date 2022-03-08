import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Request } from '../model/request';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(
  private http: HttpClient,
  private auth: AuthenticationService
  ) { }

  private requestUrl = 'http://localhost:8081/myApp/api/requests';
  private token = this.auth.getToken();
  private reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.token);

  public getAllRequest(id: number) {
	return this.http.get<Request[]>(`${this.requestUrl}/getAllRequests/${id}`,{headers: this.reqHeader});
  }

  //request id
  public getRequest(id: number){
    return this.http.get<Request[]>(`${this.requestUrl}/getRequest/${id}`, {headers: this.reqHeader})
  }

  public createRequest(id: number,textRequest: string){
	return this.http.get(`${this.requestUrl}/createRequest/${id}/${textRequest}`, {headers: this.reqHeader});
  }
}
