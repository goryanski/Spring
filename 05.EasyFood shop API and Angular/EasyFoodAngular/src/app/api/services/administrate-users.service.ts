import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {EditProductInterface} from "../interfaces/edit-product.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {ProductLinkedDataResponseInterface} from "../interfaces/responses/product-linked-data-response.interface";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";

@Injectable()
export class AdministrateUsersService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly localStorage: BrowserLocalStorage
  ) {
  }

  getUserIdByUsername(username: string): Observable<number> {
    return this.httpClient.get<number>(
      [
        this.appEnv.apiEasyFoodURL,
        'users',
        'getId',
        `${username}`
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

  blockUser(userId: number)
    : Observable<MessageResponseInterface> {
    return this.httpClient.put<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'blockUser',
      ].join('/'),
      userId,
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

  setUserAsAdmin(userId: number)
    : Observable<MessageResponseInterface> {
    return this.httpClient.put<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'setUserAsAdmin',
      ].join('/'),
      userId,
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
