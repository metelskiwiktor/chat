import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-mock',
  templateUrl: './mock.component.html',
  styleUrls: ['./mock.component.css']
})

export class MockComponent implements OnInit {
  date = '';

  constructor(private httpService: HttpService) {
  }

  ngOnInit(): void {
    this.initMockDate();
  }

  initMockDate() {
    this.httpService.getMockDate().subscribe(value => {
      this.date = value;
    });
  }

}
