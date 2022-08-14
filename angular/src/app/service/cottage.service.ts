import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core'
import { Cottage } from '../model/cottage';
import { Image } from '../model/image';
import { AuthenticationService } from './authentication.service';


@Injectable({
  providedIn: 'root'
})
export class CottageService {

    private cottageUrl = 'http://localhost:8081/myApp/api/cottages';

  constructor(
    private http: HttpClient,
    private auth: AuthenticationService,
  ) {
  }

  public createCottage(cottage: Cottage, id: number) {
    return this.http.post(`${this.cottageUrl}/createCottage/${id}`, cottage);
  }

  public updateCottage(cottage: Cottage) {
    return this.http.put(`${this.cottageUrl}/updateCottage`, cottage);
  }

  public getCottage(id: number) {
    return this.http.get<Cottage>(`${this.cottageUrl}/getCottageById/${id}`);
  }

  public deleteCottage(id: number) {
    return this.http.get<Cottage>(`${this.cottageUrl}/deleteCottage/${id}`);
  }

  public getAllOwnerCottages(id: number) {
    return this.http.get<Cottage[]>(`${this.cottageUrl}/getAllOwnerCottages/${id}`);
  }

  public getAllCottages() {
    return this.http.get<Cottage[]>(`${this.cottageUrl}/getAllCottages`);
  }


}