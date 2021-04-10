import {Injectable, NgZone} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {LoginModel} from '../model/LoginModel';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  // private baseUrl = 'http://localhost:8080';

  private baseUrl = 'https://chat-wm-api.azurewebsites.net';

  constructor(private http: HttpClient, private router: Router, private ngZone: NgZone) {
  }


  getMockDate() {
    return this.http.get(this.baseUrl + '/mock', {responseType: 'text'});
  }

  register(registerModel: LoginModel) {
    return this.http.post(this.baseUrl + '/users/register', registerModel, {responseType: 'json'});
  }

  login(loginModel: LoginModel) {
    return this.http.post<any>(this.baseUrl + '/login', loginModel, {responseType: 'json'});
  }

  getAllRooms() {
    return this.http.get(this.baseUrl + '/message/room/all-rooms', {responseType: 'json'});
  }

  getRoom(id) {
    return this.http.get(this.baseUrl + '/message/room/' + id, {responseType: 'json'});
  }

  sendRoomMessage(id, message) {
    return this.http.post<any>(this.baseUrl + '/message/room/' + id, message, {headers: this.getRequestHeaders()});
  }

  createRoom(roomName) {
    return this.http.post<any>(this.baseUrl + '/message/room/create', roomName, {headers: this.getRequestHeaders()});
  }

  getProfile(id) {
    return this.http.get<any>(this.baseUrl + '/users/' + id, {responseType: 'json'});
  }

  getConversations() {
    return this.http.get<any>(this.baseUrl + '/message/private/conversations', {headers: this.getRequestHeaders()});
  }

  deleteRoomMessage(id) {
    let params = new HttpParams();
    params = params.append('message-id', id);
    return this.http.delete(this.baseUrl + '/message/room', {params, headers: this.getRequestHeaders()});
  }

  getRequestHeaders() {
    this.checkExpiration();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: this.getJwt()
    });
  }

  storeJwt(jwt) {
    const helper = new JwtHelperService();
    const decodeToken = helper.decodeToken(jwt);
    localStorage.setItem('username', decodeToken.sub);
    localStorage.setItem('roles', decodeToken.roles);
    localStorage.setItem('jwt', jwt);
    localStorage.setItem('userId', decodeToken.userId);
  }

  isModerator() {
    return this.getRole().includes('ROLE_MODERATOR');
  }

  getUserId() {
    return localStorage.getItem('userId');
  }

  getJwt() {
    return localStorage.getItem('jwt');
  }

  isLoggedIn() {
    return this.getUsername() != null;
  }

  getRole() {
    return localStorage.getItem('roles');
  }

  getUsername() {
    return localStorage.getItem('username');
  }

  logout() {
    localStorage.clear();
  }

  checkExpiration() {
    if (this.getJwt() == null) {
      return;
    }
    const helper = new JwtHelperService();
    if (helper.isTokenExpired(this.getJwt())) {
      this.logout();
      alert('Twoja sesja wygasla, zaloguj sie ponownie');
      this.ngZone.run(() => this.router.navigate(['/login']));
    }
  }
}
