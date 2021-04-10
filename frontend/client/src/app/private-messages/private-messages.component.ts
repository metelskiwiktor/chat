import {Component, NgZone, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-private-messages',
  templateUrl: './private-messages.component.html',
  styleUrls: ['./private-messages.component.css']
})
export class PrivateMessagesComponent implements OnInit {

  constructor(private app: AppComponent, private route: ActivatedRoute, private httpService: HttpService,
              private router: Router, private ngZone: NgZone) { }

  ngOnInit(): void {
    this.app.setTitle('Prywatne wiadomosci');
    this.loadConversations();
  }

  loadConversations() {
    this.httpService.getConversations().toPromise().then(value => {
      console.log(value);
    }).catch(reason => {
      alert(reason.error.message);
    });
  }

  loadConversation() {

  }
}
