import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-page-title',
  template: `
    <h1 class="title">{{title}}</h1>
    <app-standard-divider></app-standard-divider>
  `,
  styles: [`
    .title{
      padding-top: 2.5%;
      text-align: center;
      font-size: 4em;
    }
  `]
})
export class PageTitleComponent{
  @Input() title: string;
}
