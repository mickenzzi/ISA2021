import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Request } from '../model/request';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient) { }
  private requestUrl = 'http://localhost:8081/myApp/api/requests';
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser')); 
  reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.currentUser.token);
  
  public getAllRequest(id: number) {
	return this.http.get<Request[]>(`${this.requestUrl}/getAllRequests/${id}`,{headers: this.reqHeader});
  }
  
  public createRequest(id: number,textRequest: string){
	return this.http.get(`${this.requestUrl}/createRequest/${id}/${textRequest}`, {headers: this.reqHeader});
  }
}
