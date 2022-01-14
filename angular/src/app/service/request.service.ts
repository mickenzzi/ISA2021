import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Request } from '../model/request';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient) { }
  private requestUrl = 'http://localhost:8081/myApp/api/requests';
  
  public getAllRequest(id: number) {
	return this.http.get<Request[]>(`${this.requestUrl}/getAllRequests/${id}`);
  }
  
  public createRequest(id: number,textRequest: string){
	return this.http.get(`${this.requestUrl}/createRequest/${id}/${textRequest}`);
  }
}
