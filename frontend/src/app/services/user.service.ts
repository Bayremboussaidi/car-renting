import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model'; // Ensure correct import path

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8084/api/users'; // Adjust if needed

  constructor(private http: HttpClient) {}

  // ✅ Fetch all users (kept 'any[]' for safety if response format is inconsistent)
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  // ✅ Delete a user (unchanged since it's already correct)
  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${userId}`);
  }

  // ✅ Update a user (kept 'any' for compatibility but ensured 'id' is number)
  updateUser(id: number, userData: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, userData);
  }

  // ✅ Create a user (kept 'any' for flexibility but used 'User' as return type)
  createUser(userData: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, userData);
  }
}
