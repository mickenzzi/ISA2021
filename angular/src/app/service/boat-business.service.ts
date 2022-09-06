import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BoatBusinessService {

  private businessUrl = 'http://localhost:8081/myApp/api/boatBusiness';

  constructor(private http: HttpClient) {
  }

  public getProfit(id: number, start: String, end: String) {
    return this.http.get<number>(`${this.businessUrl}/profit/${id}/${start}/${end}`);
  }

  public getReservationsPerMonth(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/reservationsPerMonth/${id}`);
  }

  public getReservationsPerWeek(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/reservationsPerWeek/${id}`);
  }

  public getReservationsPerDay(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/reservationsPerDay/${id}`);
  }

  public getReservedDaysPerMonth(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/reservedDaysPerMonth/${id}`);
  }

  public getReservedDaysPerWeek(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/reservedDaysPerWeek/${id}`);
  }

  public getReservedDays(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/reservedDays/${id}`);
  }

  public getMonthlyProfit(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/monthlyProfit/${id}`);
  }

  public getWeeklyProfit(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/weeklyProfit/${id}`);
  }

  public getDailyProfit(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/dailyProfit/${id}`);
  }
  
  public getBoatProfit(id: number, start: String, end: String) {
    return this.http.get<number>(`${this.businessUrl}/boatProfit/${id}/${start}/${end}`);
  }

  public getBoatReservationsPerMonth(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatReservationsPerMonth/${id}`);
  }

  public getBoatReservationsPerWeek(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatReservationsPerWeek/${id}`);
  }

  public getBoatReservationsPerDay(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatReservationsPerDay/${id}`);
  }

  public getBoatReservedDaysPerMonth(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatReservedDaysPerMonth/${id}`);
  }

  public getBoatReservedDaysPerWeek(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatReservedDaysPerWeek/${id}`);
  }

  public getBoatReservedDays(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatReservedDays/${id}`);
  }

  public getBoatMonthlyProfit(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatMonthlyProfit/${id}`);
  }

  public getBoatWeeklyProfit(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatWeeklyProfit/${id}`);
  }

  public getBoatDailyProfit(id: number) {
    return this.http.get<number[]>(`${this.businessUrl}/boatDailyProfit/${id}`);
  }
}
