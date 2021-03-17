import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginModel} from '../model/LoginModel';

@Injectable({
  providedIn: 'root'
})
export class HttpMockService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }


  getMockDate() {
    // const reqHeader = new HttpHeaders({
    //   'Content-Type': 'application/json',
    // tslint:disable-next-line:max-line-length
    //   Authorization: 'Bearer ' + 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWhhanN0ZXIxMjMiLCJyb2xlcyI6W10sImV4cCI6MTYxNTkzMTcwNX0.Yt1y1dXUPvSR81g2-IiMkuQxpm5MSeKJLQpzJgEQtws'
    // });

    return this.http.get(this.baseUrl + '/mock', {responseType: 'text'});
  }

  register(registerModel: LoginModel) {
    return this.http.post(this.baseUrl + '/users/register', registerModel, {responseType: 'json'});
  }

  login(loginModel: LoginModel) {
    return this.http.post<any>(this.baseUrl + '/login', loginModel, {responseType: 'json'});
  }
}
