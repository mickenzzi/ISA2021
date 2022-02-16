import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
    //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser')); 
  reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.currentUser.token);
  public createAdventure(adventure: Adventure,id: number) {
	return this.http.post(`${this.adventureUrl}/createAdventure/${id}`, adventure, {headers: this.reqHeader});
  }
   //id instruktora 
  public getAllAdventures(id: number) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/getAllAdventures/${id}`, {headers: this.reqHeader});
  }
  public getAdventure(id: number) {
	return this.http.get<Adventure>(`${this.adventureUrl}/getAdventureById/${id}`, {headers: this.reqHeader});
  }
  public deleteAdventure(id: number){
	return this.http.get<Adventure>(`${this.adventureUrl}/deleteAdventure/${id}`, {headers: this.reqHeader});
  }
   public updateAdventure(adventure: Adventure){
	return this.http.post(`${this.adventureUrl}/updateAdventure`, adventure, {headers: this.reqHeader});
  }
  
  public getSearchAdventures(id: number, search: string){
	return this.http.get<Adventure[]>(`${this.adventureUrl}/getSearchAdventures/${id}/${search}`, {headers: this.reqHeader});
  }
  
  public sortAdventuresByTitle(id: number, asc: boolean) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/sortAdventuresByTitle/${id}/${asc}`, {headers: this.reqHeader});
  }
  public sortAdventuresByPrice(id: number, asc: boolean) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/sortAdventuresByPrice/${id}/${asc}`, {headers: this.reqHeader});
  }
  public sortAdventuresByCapacity(id: number, asc: boolean) {
	return this.http.get<Adventure[]>(`${this.adventureUrl}/sortAdventuresByCapacity/${id}/${asc}`, {headers: this.reqHeader});
  }
  public getAllTermins(id: number) {
	return this.http.get<Termin[]>(`${this.terminUrl}/getAllTerminsInstructor/${id}`, {headers: this.reqHeader});
  }
  
   public getAllReservation(id: number) {
	return this.http.get<Reservation[]>(`${this.terminUrl}/getAllReservationInstructor/${id}`, {headers: this.reqHeader});
  }
  
   public createAction(idInstructor: number, idAdventure: number, termin: Termin){
	return this.http.post(`${this.adventureUrl}/createAction/${idInstructor}/${idAdventure}`, termin, {headers: this.reqHeader});
  }
  
   public createReservation(start: string, end: string, adventureId: number, userId: number){
	return this.http.get(`${this.terminUrl}/createReservation/${start}/${end}/${adventureId}/${userId}`, {headers: this.reqHeader});
  }
   public deleteTermin(id: number){
	return this.http.get<Termin>(`${this.terminUrl}/deleteTermin/${id}`, {headers: this.reqHeader});
  }
  public deleteReservation(id: number){
	return this.http.get<Reservation>(`${this.terminUrl}/deleteReservation/${id}`, {headers: this.reqHeader});
  }
   public deleteReservationTermin(id: number,start: string, end: string){
	return this.http.get<Reservation>(`${this.terminUrl}/deleteReservationTermin/${id}/${start}/${end}`, {headers: this.reqHeader});
  }
  public getTermin(id: number){
	return this.http.get<Termin>(`${this.terminUrl}/getTerminById/${id}`, {headers: this.reqHeader});
  }
   public updateTermin(termin: Termin){
	return this.http.post(`${this.terminUrl}/updateTermin`, termin, {headers: this.reqHeader});
  }
  
}
