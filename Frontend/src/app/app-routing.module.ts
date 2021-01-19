import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomepageComponent} from './main/homepage/homepage.component';
import {NotFoundComponent} from './main/not-found/not-found.component';

// KEEP WILDCARD AT THE BOTTOM
const routes: Routes = [
  {path: '', component: HomepageComponent, pathMatch: 'full'},
  {path: 'not-found', component: NotFoundComponent},
  {path: '**', redirectTo: '/not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
