import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Adventure } from '../model/adventure';
import { Termin } from '../model/termin';
import { Reservation } from '../model/reservation';

@Injectable({
  providedIn: 'root'
})
export class AdventureService {

  constructor(private http: HttpClient) { }
  private adventureUrl = 'http://localhost:8081/myApp/api/adventures';
  private terminUrl = 'http://localhost:8081/myApp/api/termins';
  
   public createAdventure(adventure: Adventure,id: number) {
	return this.http.post(`${this.adventureUrl}/createAdventure/${id}`, adventure);
   }
   //id instruktora 
   public getAllAdventures(id: number) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/getAllAdventures/${id}`);
  }
  public getAdventure(id: number) {
	return this.http.get<Adventure>(`${this.adventureUrl}/getAdventureById/${id}`);
  }
  public deleteAdventure(id: number){
	return this.http.get<Adventure>(`${this.adventureUrl}/deleteAdventure/${id}`);
  }
   public updateAdventure(adventure: Adventure){
	return this.http.post(`${this.adventureUrl}/updateAdventure`, adventure);
  }
  
  public getSearchAdventures(id: number, search: string){
	return this.http.get<Adventure[]>(`${this.adventureUrl}/getSearchAdventures/${id}/${search}`);
  }
  
  public sortAdventuresByTitle(id: number, asc: boolean) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/sortAdventuresByTitle/${id}/${asc}`);
  }
  public sortAdventuresByPrice(id: number, asc: boolean) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/sortAdventuresByPrice/${id}/${asc}`);
  }
  public sortAdventuresByCapacity(id: number, asc: boolean) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/sortAdventuresByCapacity/${id}/${asc}`);
  }
  public getAllTermins(id: number) {
	return this.http.get<Termin[]>(`${this.terminUrl}/getAllTerminsInstructor/${id}`);
  }
  
   public getAllReservation(id: number) {
	return this.http.get<Reservation[]>(`${this.terminUrl}/getAllReservationInstructor/${id}`);
  }
  
   public createAction(idInstructor: number, idAdventure: number, termin: Termin){
	return this.http.post(`${this.adventureUrl}/createAction/${idInstructor}/${idAdventure}`, termin);
  }
  
   public createReservation(start: string, end: string, adventureId: number, userId: number){
	return this.http.get(`${this.terminUrl}/createReservation/${start}/${end}/${adventureId}/${userId}`);
  }
   public deleteTermin(id: number){
	return this.http.get<Termin>(`${this.terminUrl}/deleteTermin/${id}`);
  }
  public deleteReservation(id: number){
	return this.http.get<Reservation>(`${this.terminUrl}/deleteReservation/${id}`);
  }
   public deleteReservationTermin(id: number,start: string, end: string){
	return this.http.get<Reservation>(`${this.terminUrl}/deleteReservationTermin/${id}/${start}/${end}`);
  }
  public getTermin(id: number){
	return this.http.get<Termin>(`${this.terminUrl}/getTerminById/${id}`);
  }
   public updateTermin(termin: Termin){
	return this.http.post(`${this.terminUrl}/updateTermin`, termin);
  }
  
}
