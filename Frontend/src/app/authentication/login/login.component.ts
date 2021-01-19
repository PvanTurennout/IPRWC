import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AuthenticationService} from '../authentication.service';
import {MatDialog} from '@angular/material/dialog';
import {Account} from '../../typescript/model/account.model';
import {Router} from '@angular/router';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;

  notLoading = true;
  stayLoggedIn = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthenticationService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {

    this.loginFormGroup = this.formBuilder.group({
      email: '',
      password: ''
    });
  }

  onLogin(){
    const account = new Account(this.loginFormGroup.controls.email.value, this.loginFormGroup.controls.password.value);
    this.loginRequest(account);
  }

  loginRequest(account: Account){
    // this.notLoading = false;

    this.authService.login(
      account,
      this.stayLoggedIn
    ).subscribe(() => {
        // this.notLoading = true;
        this.router.navigate(['/']);
      },
      error => {
        // this.notLoading = true;
        displayHttpError(error, this.dialog);
      });
  }
}
