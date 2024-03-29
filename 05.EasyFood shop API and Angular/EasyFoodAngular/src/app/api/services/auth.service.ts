import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {LoginPersonRequestInterface} from "../interfaces/requests/login-person-request.interface";
import {JwtResponse} from "../interfaces/responses/jwt-response.interface";
import {RegisterPersonRequestInterface} from "../interfaces/requests/register-person-request.interface";

@Injectable()
export class AuthService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {}

  registerPerson(requestObject: RegisterPersonRequestInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'auth',
        'registration'
      ].join('/'),
      requestObject
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }

  login(requestObject: LoginPersonRequestInterface): Observable<JwtResponse> {
    return this.httpClient.post<JwtResponse>(
      [
        this.appEnv.apiEasyFoodURL,
        'auth',
        'login'
      ].join('/'),
      requestObject
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
