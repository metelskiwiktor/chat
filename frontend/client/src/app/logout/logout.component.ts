import {Component, NgZone, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import {Router} from '@angular/router';

@Component({
  template: `
    logout
  `,
  selector: 'app-logout'
})
export class LogoutComponent implements OnInit {

  constructor(private httpService: HttpService, private router: Router, private ngZone: NgZone) {
  }

  ngOnInit(): void {
    this.httpService.logout();
    this.ngZone.run(() => this.router.navigate(['/']));
  }

}
