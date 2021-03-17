import {Component, NgZone, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {LoginModel} from '../../model/LoginModel';
import {HttpMockService} from '../http-mock.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  username: string;
  password1: string;
  password2: string;

  constructor(private app: AppComponent, private httpMock: HttpMockService, private router: Router, private ngZone: NgZone) {
  }

  ngOnInit(): void {
    this.app.setTitle('Rejestracja');
  }

  register() {
    if (this.password1 !== this.password2) {
      alert('Hasła się różnią');
      return;
    }

    const registerModel = new LoginModel();
    registerModel.login = this.username;
    registerModel.password = this.password1;

    this.httpMock.register(registerModel).toPromise().then(() => {
      alert('Pomyślnie zarejestrowano');
      this.ngZone.run(() => this.router.navigate(['/login']));
    }).catch(reason => {
      alert(reason.error.message);
    });
  }
}
