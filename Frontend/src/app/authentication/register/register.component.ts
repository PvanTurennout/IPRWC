import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CustomValidators} from '../../typescript/utils/custom-validators';
import {AuthenticationService} from '../authentication.service';
import {Account} from '../../typescript/model/account.model';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterComponent implements OnInit {
  disabled = false;
  registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthenticationService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      email: '',
      password: '',
      repeatPassword: ''
    }, { validator: CustomValidators.passwordRepeat }
    );
  }

  onRegister(){
    const account = new Account(this.registerForm.controls.email.value, this.registerForm.controls.password.value);
    this.registerRequest(account);
  }

  registerRequest(account: Account){
    // this.loading = true;

    this.authService.register(
      account
    ).subscribe(() => {
        // this.loading = false;
      this.router.navigate(['/login']);
      },
      error => {
        displayHttpError(error, this.dialog);
        // this.loading = false;
      });
  }

}
