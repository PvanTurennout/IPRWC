import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from '../../typescript/utils/custom-validators';

@Component({
  selector: 'app-input-email',
  styleUrls: ['./input.components.scss'],
  template: `
    <div [formGroup]="emailFormGroup">
      <mat-form-field [style.min-width.px]=240 [style.width.vw]=20 appearance="outline">
        <mat-label>Enter your email</mat-label>
        <input matInput placeholder="adress@example.com" formControlName="email" required>
        <mat-error *ngIf="emailFormGroup.controls.email.invalid">{{getMailErrorMessage()}}</mat-error>
      </mat-form-field>
    </div>
  `
})
export class EmailInputComponent implements OnInit {

  @Input() emailFormGroup: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.setValidators();
  }

  setValidators() {
    this.emailFormGroup.controls.email.setValidators([Validators.required, CustomValidators.email, CustomValidators.noWhitespace]);
  }

  getMailErrorMessage(): string {
    if (this.emailFormGroup.controls.email.hasError('required')) {
      return 'You must enter a value';
    }

    if (this.emailFormGroup.controls.email.hasError('InvalidEmail')) {
      return 'Email is invalid';
    }

    return this.emailFormGroup.controls.email.hasError('ContainsWhitespace') ? 'Email can not contain spaces, tabs or new lines' : '';
  }
}
