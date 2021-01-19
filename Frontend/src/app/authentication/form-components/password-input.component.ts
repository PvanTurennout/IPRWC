import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from '@angular/forms';
import {CustomValidators} from '../../typescript/utils/custom-validators';

@Component({
  selector: 'app-input-password',
  styleUrls: ['./input.components.scss'],
  template: `
    <div [formGroup]="passwordFormGroup">
      <ng-content></ng-content>
      <mat-form-field [style.width.vw]=20 [style.min-width.px]=240 appearance="outline">
        <mat-label>{{label}}</mat-label>
        <input matInput [type]="hide ? 'password' : 'text'" [formControlName]="getFormControlName()" required>
        <button mat-icon-button matSuffix (click)="hide = !hide" [attr.aria-label]="'Hide password'" [attr.aria-pressed]="hide">
          <mat-icon>{{hide ? 'visibility_off' : 'visibility'}}</mat-icon>
        </button>
        <mat-error *ngIf="getFormControl().invalid">{{getPasswordErrorMessage()}}</mat-error>
      </mat-form-field>
    </div>
  `
})
export class PasswordInputComponent implements OnInit {
  @Input() repeatPassword: boolean;
  @Input() passwordFormGroup: FormGroup;

  label: string;
  hide = true;

  passwordValidators = [Validators.required, CustomValidators.password, CustomValidators.noWhitespace];
  passwordRepeatValidators = [Validators.required, CustomValidators.noWhitespace];

  constructor() { }

  ngOnInit(): void {
    this.label = this.repeatPassword ? 'Repeat Password' : 'Password';
    this.setValidators();
  }

  getFormControlName(): string {
    return this.repeatPassword ? 'repeatPassword' : 'password';
  }

  getFormControl() {
    return this.repeatPassword ? this.passwordFormGroup.controls.repeatPassword : this.passwordFormGroup.controls.password;
  }

  setValidators() {
    this.getFormControl().setValidators(this.repeatPassword ? this.passwordRepeatValidators : this.passwordValidators);
  }

  getPasswordErrorMessage(): string {
    if (this.getFormControl().hasError('required')) {
      return 'You must enter a value';
    }

    if (this.getFormControl().hasError('PasswordRequirements')){
      return 'Password does not check all requirements';
    }

    if (this.getFormControl().hasError('NoPasswordMatch')){
      return 'Password does not match';
    }

    return this.getFormControl().hasError('ContainsWhitespace') ? 'Password can not contain spaces, tabs or new lines' : '';
  }

}
