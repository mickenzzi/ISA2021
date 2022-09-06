import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Boat } from '../model/boat';
import { CottageImage } from '../model/cottageImage';
import { EntitySubscriber } from '../model/entity-subscriber';
import { Image } from '../model/image';
import { Termin } from '../model/termin';
import { TerminBoat } from '../model/terminBoat';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class BoatService {

  private boatUrl = 'http://localhost:8081/myApp/api/boats';
  private terminUrl = 'http://localhost:8081/myApp/api/boatTermins';

constructor(
  private http: HttpClient,
  private auth: AuthenticationService,
) {
}

public reserveTermin(termin: TerminBoat) {
  return this.http.put(`${this.terminUrl}/reserveTermin`, termin);
}

public cancelReservation(termin: TerminBoat) {
  return this.http.put(`${this.terminUrl}/cancelReservation`, termin);
}

public subscribe (entity: EntitySubscriber){
  return this.http.post(`${this.boatUrl}/subscribe`, entity);
}

public unsubscribe(cottageId?: number, userId?: number) {
  return this.http.get<EntitySubscriber>(`${this.boatUrl}/unsubscribe/${cottageId}/${userId}`);
}

public getAllCottageSubs(id?: number) {
  return this.http.get<EntitySubscriber[]>(`${this.boatUrl}/getAllSubscribers/${id}`);
}

public createCottage(cottage: Boat, id: number) {
  return this.http.post(`${this.boatUrl}/createCottage/${id}`, cottage);
}

public updateCottage(cottage: Boat) {
  return this.http.put(`${this.boatUrl}/updateCottage`, cottage);
}

public getCottage(id: number) {
  return this.http.get<Boat>(`${this.boatUrl}/getCottageById/${id}`);
}

public deleteCottage(id: number) {
  return this.http.get<Boat>(`${this.boatUrl}/deleteCottage/${id}`);
}

public getAllOwnerCottages(id: number) {
  return this.http.get<Boat[]>(`${this.boatUrl}/getAllOwnerCottages/${id}`);
}

public getAllCottages() {
  return this.http.get<Boat[]>(`${this.boatUrl}/getAllCottages`);
}

public getAllCottageImages(id: number) {
  return this.http.get<CottageImage[]>(`${this.boatUrl}/getAllCottageImages/${id}`);
}


public updateImage(image: Image) {
  return this.http.put(`${this.boatUrl}/updateImage`, image);
}

public deleteCottageImage(id: number) {
  return this.http.get<CottageImage>(`${this.boatUrl}/deleteImage/${id}`);
}

public saveImage(id: number, url: String) {
  return this.http.post(`${this.boatUrl}/saveImage/${id}`, url);
}

public createTermin(termin: Termin, cottageId: number, i: number){
  return this.http.post(`${this.terminUrl}/createTermin/${cottageId}/${i}`, termin);
}

public getCottageTermins(id: number) {
  return this.http.get<TerminBoat[]>(`${this.terminUrl}/getAllTermins/${id}`);
}

public getFinishedTermins(id: number) {
  return this.http.get<TerminBoat[]>(`${this.terminUrl}/getFinishedTermins/${id}`);
}

public getCottageTermin(id: number) {
  return this.http.get<TerminBoat>(`${this.terminUrl}/getTermin/${id}`);
}

public updateCottageTermin(termin: TerminBoat) {
  return this.http.put(`${this.terminUrl}/updateTermin`, termin);
}

}
