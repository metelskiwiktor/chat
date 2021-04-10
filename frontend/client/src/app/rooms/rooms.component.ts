import {Component, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent implements OnInit {
  rooms: any;
  roomName: string;
  loggedIn: boolean;

  constructor(private app: AppComponent, private httpService: HttpService) {
  }

  ngOnInit(): void {
    this.app.setTitle('Pokoje');
    this.loadRooms();
    this.loggedIn = this.httpService.isLoggedIn();
  }

  loadRooms() {
    this.httpService.getAllRooms().subscribe(value => {
      this.rooms = value;
    });
  }

  createRoom() {
    this.httpService.createRoom(this.roomName).toPromise().then(() => {
      this.loadRooms();
      this.roomName = '';
    }).catch(reason => {
      alert(reason.error.message);
    });
  }
}
