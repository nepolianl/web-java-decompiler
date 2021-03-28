import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestClientService {

  constructor(private http: HttpClient) { }

  post(url: string, data: any): Observable<any> {
    let options = {
      headers: this.httpHeaders(),
      reportProgress: true, 
      responseType: 'json'
    };
    return this.http.post<any>(url, data, { reportProgress: true, responseType: 'json', observe: 'events' });
  }

  get(url: string, queryParams: any): Observable<any> {
    let options = {
      headers : this.httpHeaders(),
      params: queryParams
    };
    return this.http.get<any>(url, options)
  }

  httpHeaders() {
    // Browser takes care of multipart/form-data with data boundary calc
    let headers = new HttpHeaders({
    });
    return headers;
  }
}
