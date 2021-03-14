import {Component, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-private-messages',
  templateUrl: './private-messages.component.html',
  styleUrls: ['./private-messages.component.css']
})
export class PrivateMessagesComponent implements OnInit {

  constructor(private app: AppComponent) { }

  ngOnInit(): void {
    this.app.setTitle('Prywatne wiadomosci');
  }

}
