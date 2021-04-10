import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MockComponent} from './mock/mock.component';
import {PrivateMessagesComponent} from './private-messages/private-messages.component';
import {ProfileComponent} from './profile/profile.component';
import {RoomComponent} from './room/room.component';
import {RoomsComponent} from './rooms/rooms.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {LogoutComponent} from './logout/logout.component';

const routes: Routes = [
  {path: '', component: RoomsComponent},
  {path: 'mock', component: MockComponent},
  {path: 'private-messages', component: PrivateMessagesComponent},
  {path: 'private-messages/:id', component: PrivateMessagesComponent},
  {path: 'profile/:id', component: ProfileComponent},
  {path: 'room/:id', component: RoomComponent},
  {path: 'rooms', component: RoomsComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routingComponents = [
  PrivateMessagesComponent, ProfileComponent, RoomsComponent, RoomComponent, LoginComponent, RegisterComponent
];
