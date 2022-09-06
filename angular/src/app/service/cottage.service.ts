import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core'
import { Cottage } from '../model/cottage';
import { Image } from '../model/image';
import { AuthenticationService } from './authentication.service';
import { CottageImage } from '../model/cottageImage';
import { Termin } from '../model/termin';
import { TerminCottage } from '../model/terminCottage';
import { EntitySubscriber } from '../model/entity-subscriber';


@Injectable({
  providedIn: 'root'
})
export class CottageService {

    private cottageUrl = 'http://localhost:8081/myApp/api/cottages';
    private terminUrl = 'http://localhost:8081/myApp/api/cottageTermins';

  constructor(
    private http: HttpClient,
    private auth: AuthenticationService,
  ) {
  }

  public subscribe (entity: EntitySubscriber){
    return this.http.post(`${this.cottageUrl}/subscribe`, entity);
  }

  public unsubscribe(cottageId?: number, userId?: number) {
    return this.http.get<EntitySubscriber>(`${this.cottageUrl}/unsubscribe/${cottageId}/${userId}`);
  }

  public getAllCottageSubs(id?: number) {
    return this.http.get<EntitySubscriber[]>(`${this.cottageUrl}/getAllSubscribers/${id}`);
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
  
  public getAllCottageImages(id: number) {
    return this.http.get<CottageImage[]>(`${this.cottageUrl}/getAllCottageImages/${id}`);
  }


  public updateImage(image: Image) {
    return this.http.put(`${this.cottageUrl}/updateImage`, image);
  }

  public deleteCottageImage(id: number) {
    return this.http.get<CottageImage>(`${this.cottageUrl}/deleteImage/${id}`);
  }

  public saveImage(id: number, url: String) {
    return this.http.post(`${this.cottageUrl}/saveImage/${id}`, url);
  }

  public createTermin(termin: Termin, cottageId: number){
    return this.http.post(`${this.terminUrl}/createTermin/${cottageId}`, termin);
  }

  public getCottageTermins(id: number) {
    return this.http.get<TerminCottage[]>(`${this.terminUrl}/getAllTermins/${id}`);
  }

  public getCottageTermin(id: number) {
    return this.http.get<TerminCottage>(`${this.terminUrl}/getTermin/${id}`);
  }

  public updateCottageTermin(termin: TerminCottage) {
    return this.http.put(`${this.terminUrl}/updateTermin`, termin);
  }

}