import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";
import {Observable, of, throwError} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {BrowserLocalStorage} from "../storage/local-storage";
import {AuthHelper} from "../helpers/auth-helper";

@Injectable()
export class QueryHttpInterceptor implements HttpInterceptor {
  constructor(
    private readonly router: Router,
    private readonly browserLocalStorage: BrowserLocalStorage,
    private readonly authHelper: AuthHelper
  ) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
       return next.handle(req).pipe(
         catchError(err => {
           console.log('interceptor caught the response error!');
           if (err.status === 401) {
             this.authHelper.logOut();
             console.log('error 401 - Unauthorized');
             return of(err.message);
           }
           if (err.status === 403) {
             console.log('error 403 - Forbidden');
             return of(err.message);
           }
           return throwError(err);
         })
       );

    // if we want to add header with token to all requests
    //return next.handle(this.addAuthToken(req));
  }

  // addAuthToken(request: HttpRequest<any>) {
  //   return request.clone({
  //     setHeaders: {
  //       Authorization: `Bearer ${this.browserLocalStorage.getItem('accessToken')}`
  //     }
  //   });
  // }
}

