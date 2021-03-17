import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {AppRoutingModule, routingComponents} from './app-routing.module';
import {PrivateMessageComponent} from './private-message/private-message.component';
import {MockComponent} from './mock/mock.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {RoomsComponent} from './rooms/rooms.component';
import {RoomComponent} from './room/room.component';
import {ProfileComponent} from './profile/profile.component';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    PrivateMessageComponent,
    RoomsComponent,
    RoomComponent,
    ProfileComponent,
    MockComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    RouterModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
