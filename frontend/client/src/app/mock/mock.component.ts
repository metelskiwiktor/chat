import {Component, OnInit} from '@angular/core';
import {HttpMockService} from '../http-mock.service';

@Component({
  selector: 'app-mock',
  templateUrl: './mock.component.html',
  styleUrls: ['./mock.component.css']
})

export class MockComponent implements OnInit {
  date = '';

  constructor(private httpMock: HttpMockService) {
  }

  ngOnInit(): void {
    this.initMockDate();
  }

  initMockDate() {
    this.httpMock.getMockDate().subscribe(value => {
      this.date = value;
    });
  }

}
