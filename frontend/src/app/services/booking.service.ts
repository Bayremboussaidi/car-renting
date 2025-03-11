import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking } from '../models/booking.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8084/api/bookings'; // Adjust the backend URL if needed

  constructor(private http: HttpClient) {}



  /**
   * Fetch unavailable dates for a specific car.
   * @param voitureId The ID of the car (string as required by backend)
   * @returns Observable containing unavailable dates
   */
  getUnavailableDates(voitureId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${voitureId}/availability`);
  }

  /**
   * Create a new booking for a car.
   * @param bookingData The booking details
   * @returns Observable containing booking response
   */
  createBooking(bookingData: any): Observable<any> {
    const headers = { 'Content-Type': 'application/json' };
    return this.http.post<any>(this.apiUrl, bookingData, { headers });
  }


  /**
   * Fetch all bookings.
   * @returns Observable containing a list of all bookings
   */
  getAllBookings(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/all`);
  }

  /**
   * Update booking status.
   * @param id Booking ID
   * @param status New status (PENDING, CONFIRMED, CANCELED)
   * @returns Observable with updated booking data
   */
  updateBookingStatus(id: number, status: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/status`, { "status": status }); // ✅ Explicit field name
}







  getBookingsByStatus(status: string) {
    return this.http.get<{ success: boolean; data: Booking[] }>(
      `your-api-url/bookings?status=${status}`
    );
  }

}
