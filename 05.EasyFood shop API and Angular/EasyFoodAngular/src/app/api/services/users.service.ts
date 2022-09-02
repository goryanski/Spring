import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {UserInterface} from "../interfaces/user.interface";
import {UpdatePersonRequestInterface} from "../interfaces/requests/update-person-request.interface";

@Injectable()
export class UsersService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly localStorage: BrowserLocalStorage
  ) {}


  getUserInfo(userId: number): Observable<UserInterface> {
    return this.httpClient.get<UserInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'users',
        `${userId}`
      ].join('/'),
      {
        headers: {
          'Authorization': `Bearer ${this.localStorage.getItem('accessToken')}`
        }
      }
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }


  editPerson(person: UpdatePersonRequestInterface): Observable<MessageResponseInterface> {
    return this.httpClient.put<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'users',
        'update'
      ].join('/'),
      person,
      {
        headers: {
          'Authorization': `Bearer ${this.localStorage.getItem('accessToken')}`
        }
      }
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
