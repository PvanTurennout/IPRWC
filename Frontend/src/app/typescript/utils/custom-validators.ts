import {AbstractControl} from '@angular/forms';

export class CustomValidators{

  static email(control: AbstractControl) {
    const regex = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;

    if (!regex.test(control.value)){
      return { InvalidEmail: true };
    }
  }

  static password(control: AbstractControl) {
    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[\W_]).{8,60}$/;

    if (!regex.test(control.value)){
      return { PasswordRequirements: true };
    }
  }

  static noWhitespace(control: AbstractControl) {
    if (/(\s)/.test(control.value)){
      return { ContainsWhitespace: true};
    }
  }

  // Works only as FormGroup option
  static passwordRepeat(control: AbstractControl) {
    const password: string = control.get('password').value;
    const confirmPassword: string = control.get('repeatPassword').value;

    if (password !== confirmPassword) {
      control.get('repeatPassword').setErrors({ NoPasswordMatch: true });
    }
  }
}
