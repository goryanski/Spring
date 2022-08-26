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
        // Save data in response to localStorage
        this.localStorage.setItem('accessToken', response.accessToken);
        this.localStorage.setItem('currentUserRole', response.userRole);
        this.localStorage.setItem('currentUserId', response.userId);
      }),
      // return only exception to login.component.ts (to show there what happened)
      map(response => response.exception)
    );
  }
}
