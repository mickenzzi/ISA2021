import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Adventure } from '../model/adventure';

@Injectable({
  providedIn: 'root'
})
export class AdventureService {

  constructor(private http: HttpClient) { }
  private adventureUrl = 'http://localhost:8081/myApp/api/adventures';
  
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
}
