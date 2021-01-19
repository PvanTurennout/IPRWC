import {HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AuthenticationService} from '../../authentication/authentication.service';

@Injectable({providedIn: 'root'})
export class AuthInterceptorService implements HttpInterceptor{

  intercept(request: HttpRequest<any>, next: HttpHandler){
    const user = this.authService.userSubject.value;

    let jwt = '';
    if (user) {
      jwt = user.token ? user.token : '';
    }

    const modifiedRequest = request.clone({headers: request.headers.append('Authorization', 'Bearer ' + jwt)});
    return next.handle(modifiedRequest);
  }

  constructor(private authService: AuthenticationService) {
  }
}
