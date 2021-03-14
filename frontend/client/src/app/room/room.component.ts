import { Component, OnInit } from '@angular/core';
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

  constructor(private app: AppComponent) { }

  ngOnInit(): void {
    this.app.setTitle('Pokój ukrasc konie');
  }
}
