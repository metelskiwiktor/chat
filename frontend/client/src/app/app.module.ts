import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MockComponent } from './mock/mock.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import {AppRoutingModule, routingComponents} from './app-routing.module';
import {PrivateMessagesComponent} from "./private-messages/private-messages.component";
import {ProfileComponent} from "./profile/profile.component";
import {RoomComponent} from "./room/room.component";
import {RoomsComponent} from "./rooms/rooms.component";


const appRoutes: Routes = [
  { path: 'mock', component: MockComponent },
  { path: 'private-messages', component: PrivateMessagesComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'room', component: RoomComponent },
  { path: 'rooms', component: RoomsComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
  ],
  imports: [
    AppRoutingModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true}
    ),
    BrowserModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
