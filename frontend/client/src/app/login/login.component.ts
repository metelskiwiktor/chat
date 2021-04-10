import {Component, NgZone, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {LoginModel} from '../../model/LoginModel';
import {HttpService} from '../http.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private app: AppComponent, private httpService: HttpService, private router: Router, private ngZone: NgZone) {
  }

  ngOnInit(): void {
    if (this.httpService.isLoggedIn()) {
      this.ngZone.run(() => this.router.navigate(['/']));
    }
    this.app.setTitle('Logowanie');
  }

  login() {
    const loginModel = new LoginModel();
    loginModel.login = this.username;
    loginModel.password = this.password;

    this.httpService.login(loginModel).toPromise().then(resp => {
      this.httpService.storeJwt('Bearer ' + resp.token);
      this.httpService.getRole();
      this.ngZone.run(() => this.router.navigate(['/']));
    }).catch(() => {
      alert('Niepoprawny login bądź hasło');
    });
  }
}
