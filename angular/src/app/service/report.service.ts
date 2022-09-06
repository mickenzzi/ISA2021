import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Report } from '../model/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private reportUrl = 'http://localhost:8081/myApp/api/reports';

  constructor(private http: HttpClient) {
  }

  public create(boat: Report) {
    return this.http.post(`${this.reportUrl}/create`, boat);
  }

  public update(boat: Report) {
    return this.http.put(`${this.reportUrl}/update`, boat);
  }

  public getById(id: number) {
    return this.http.get<Report>(`${this.reportUrl}/getById${id}`);
  }

  public delete(id: number) {
    return this.http.get<Report>(`${this.reportUrl}/delete${id}`);
  }

  public getAll() {
    return this.http.get<Report[]>(`${this.reportUrl}/getAll`);
  }

  public getUnapproved() {
    return this.http.get<Report[]>(`${this.reportUrl}/getUnapproved`);
  }

  

}
