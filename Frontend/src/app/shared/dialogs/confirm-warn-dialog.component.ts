import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-delete-dialog',
  template: `
    <h2 mat-dialog-title>{{data.title}}</h2>
    <mat-dialog-content class="mat-typography">
      <p>{{data.text}}</p>
    </mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-button [mat-dialog-close]="true" color="warn">{{data.buttonText}}</button>
    </mat-dialog-actions>
  `,
  styles: [`

  `]
})
export class ConfirmWarnDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: {
    title: string,
    text: string,
    buttonText: string
  }) {
  }
}
