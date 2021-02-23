import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MockComponent} from './mock/mock.component';

const routes: Routes = [
  { path: '', redirectTo: 'mock', pathMatch: 'full' },
  { path: 'mock', component: MockComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(
    [
      { path: 'mock', component: MockComponent}
    ]
  )],
  exports: [RouterModule]
})
export class AppRoutingModule { }
