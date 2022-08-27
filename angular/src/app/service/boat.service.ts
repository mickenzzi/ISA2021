import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Boat } from '../model/boat';
import { CottageImage } from '../model/cottageImage';
import { Image } from '../model/image';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class BoatService {

  private boatUrl = 'http://localhost:8081/myApp/api/boats';

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

}
