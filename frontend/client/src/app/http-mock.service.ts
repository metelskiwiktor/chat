import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {from, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpMockService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }


  getMockDate() {
    return this.http.get(this.baseUrl + '/mock', {responseType: 'text'});
  }
}
