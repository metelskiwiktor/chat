import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MockComponent} from './mock/mock.component';
import {PrivateMessagesComponent} from './private-messages/private-messages.component';
import {ProfileComponent} from './profile/profile.component';
import {RoomComponent} from './room/room.component';
import {RoomsComponent} from './rooms/rooms.component';
import {PrivateMessageComponent} from './private-message/private-message.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';

const routes: Routes = [
  {path: '', component: RoomsComponent},
  {path: 'mock', component: MockComponent},
  {path: 'private-messages', component: PrivateMessagesComponent},
  {path: 'private-message', component: PrivateMessageComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'room', component: RoomComponent},
  {path: 'rooms', component: RoomsComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routingComponents = [
  PrivateMessagesComponent, ProfileComponent, RoomsComponent, RoomComponent, PrivateMessageComponent, LoginComponent, RegisterComponent
];
