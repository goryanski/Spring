import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {map, tap} from "rxjs/operators";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {AuthService} from "../../api/services/auth.service";
import {LoginPersonRequestInterface} from "../../api/interfaces/requests/login-person-request.interface";

@Injectable()
export class LoginService {
  constructor(
    private readonly authService: AuthService,
    private readonly localStorage: BrowserLocalStorage
  ) {
  }

  login(requestObject: LoginPersonRequestInterface): Observable<string> {
    return this.authService.login(requestObject).pipe(
      tap(response => {
        // TODO: Save token and role to localStorage
        // this.localStorage.setItem('accessToken', response.accessToken);
        // this.localStorage.setItem('y16', response.userRole); // currentUserRole
        // this.localStorage.setItem('v33', response.userId); // currentUserId
      }),
      // return only exception to login component (to show there what happened)
      map(response => response.exception)
    );
  }
}
