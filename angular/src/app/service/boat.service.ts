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

public createBoat(boat: Boat, id: number) {
  return this.http.post(`${this.boatUrl}/createBoat/${id}`, boat);
}

public updateBoat(boat: Boat) {
  return this.http.put(`${this.boatUrl}/updateBoat`, boat);
}

public getBoat(id: number) {
  return this.http.get<Boat>(`${this.boatUrl}/getBoat/${id}`);
}

public deleteBoat(id: number) {
  return this.http.get<Boat>(`${this.boatUrl}/deleteBoat/${id}`);
}

public getAllOwnerBoats(id: number) {
  return this.http.get<Boat[]>(`${this.boatUrl}/getAllOwnerBoats/${id}`);
}

public getAllBoats() {
  return this.http.get<Boat[]>(`${this.boatUrl}/getAllBoats`);
}

public getAllBoatImages(id: number) {
  return this.http.get<CottageImage[]>(`${this.boatUrl}/getAllBoatImages/${id}`);
}

public updateImage(image: Image) {
  return this.http.put(`${this.boatUrl}/updateImage`, image);
}

public deleteBoatImage(id: number) {
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

public reserveTermin(termin: TerminBoat) {
  return this.http.put(`${this.terminUrl}/reserveTermin`, termin);
}

public cancelReservation(termin: TerminBoat) {
  return this.http.put(`${this.terminUrl}/cancelReservation`, termin);
}

public subscribe (entity: EntitySubscriber){
  return this.http.post(`${this.boatUrl}/subscribe`, entity);
}

public unsubscribe(boatId?: number, userId?: number) {
  return this.http.get<EntitySubscriber>(`${this.boatUrl}/unsubscribe/${boatId}/${userId}`);
}

public getAllCottageSubs(id?: number) {
  return this.http.get<EntitySubscriber[]>(`${this.boatUrl}/getAllSubscribers/${id}`);
}

}
