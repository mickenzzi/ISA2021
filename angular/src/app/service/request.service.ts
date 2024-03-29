import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Request} from '../model/request';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private requestUrl = 'http://localhost:8081/myApp/api/requests';

  constructor(
    private http: HttpClient,
    private auth: AuthenticationService
  ) {
  }

  public getAllRequest(id: number) {
    return this.http.get<Request[]>(`${this.requestUrl}/getAllRequests/${id}`);
  }

  //request id
  public getRequest(id: number) {
    return this.http.get<Request[]>(`${this.requestUrl}/getRequest/${id}`)
  }

  public createRequest(id: number, textRequest: string) {
    return this.http.get(`${this.requestUrl}/createRequest/${id}/${textRequest}`);
  }
}
