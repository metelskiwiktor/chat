import {Component, NgZone, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userName: string;
  roomMessages: number;

  constructor(private app: AppComponent, private route: ActivatedRoute, private httpService: HttpService, private router: Router,
              private ngZone: NgZone) {
  }

  ngOnInit(): void {
    if (!this.httpService.isLoggedIn()) {
      this.ngZone.run(() => this.router.navigate(['/']));
    }
    this.app.setTitle('Profil');
    this.route.params.subscribe(params => {
      this.userName = params.id;
      this.loadProfile(this.userName);
    });
  }

  loadProfile(id) {
    this.httpService.getProfile(id).toPromise().then(value => {
      this.userName = value.username;
      this.roomMessages = value.roomMessages;
    }).catch(reason => {
      alert(reason.error.message);
      this.router.navigate(['/']);
    });
  }
}

