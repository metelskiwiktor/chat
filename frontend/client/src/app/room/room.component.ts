import {Component, NgZone, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {
  id: number;
  messages: any;
  myUsername: string;
  message: string;
  activeUsers: string[];
  moderator: boolean;

  constructor(private app: AppComponent, private route: ActivatedRoute, private httpService: HttpService,
              private router: Router, private ngZone: NgZone) {
  }

  ngOnInit(): void {
    this.app.setTitle('Pokój');
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.loadRoom(this.id);
    });
    this.moderator = this.httpService.isModerator();
  }

  loadRoom(id) {
    this.httpService.getRoom(id).subscribe(value => {
      this.messages = value;
      this.myUsername = this.httpService.getUsername();
      this.activeUsers = new Array(this.messages.size);
      for (const message of this.messages) {
        if (!this.activeUsers.includes(message.author.login)) {
          this.activeUsers.push(message.author.login);
        }
      }
      const index = this.activeUsers.indexOf(undefined, 0);
      if (index > -1) {
        this.activeUsers.splice(index, 1);
      }
    }, error => {
      this.ngZone.run(() => this.router.navigate(['/']));
    });
  }

  sendMessage() {
    this.httpService.sendRoomMessage(this.id, this.message).subscribe(() => {
      this.loadRoom(this.id);
      this.message = '';
    });
  }

  deleteMessage(id) {
    this.httpService.deleteRoomMessage(id).toPromise().then(() => {
      this.loadRoom(this.id);
    }).catch(reason => {
      alert(reason.error.message);
    });
  }
}
