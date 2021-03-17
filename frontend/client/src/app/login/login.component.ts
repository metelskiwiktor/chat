import {Component, NgZone, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {LoginModel} from '../../model/LoginModel';
import {HttpMockService} from '../http-mock.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private app: AppComponent, private httpMock: HttpMockService, private router: Router, private ngZone: NgZone) {
  }

  ngOnInit(): void {
    this.app.setTitle('Logowanie');
  }

  login() {
    const loginModel = new LoginModel();
    loginModel.login = this.username;
    loginModel.password = this.password;

    this.httpMock.login(loginModel).toPromise().then(resp => {
      alert('Pomyslnie zalogowano, token: ' + resp.token);
      this.ngZone.run(() => this.router.navigate(['/']));
    }).catch(() => {
      alert('Niepoprawny login bądź hasło');
    });
  }
}
