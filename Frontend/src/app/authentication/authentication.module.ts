import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginComponent} from './login/login.component';
import {EmailInputComponent} from './form-components/email-input.component';
import {PasswordInputComponent} from './form-components/password-input.component';
import {AuthenticationRoutingModule} from './authentication-routing.module';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {SharedModule} from '../shared/shared.module';
import {MatCheckboxModule} from '@angular/material/checkbox';



@NgModule({
  declarations: [
    LoginComponent,
    EmailInputComponent,
    PasswordInputComponent,
    RegisterComponent
  ],
  imports: [
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    CommonModule,
    AuthenticationRoutingModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    SharedModule,
    MatCheckboxModule,
    FormsModule
  ]
})
export class AuthenticationModule { }
