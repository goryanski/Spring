import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {MakeOrderRequestInterface} from "../interfaces/requests/make-order-request.interface";

@Injectable()
export class OrdersService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly browserLocalStorage: BrowserLocalStorage
  ) {}

  makeOrder(requestObject: MakeOrderRequestInterface)
    : Observable<MessageResponseInterface>{
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'orders',
        'makeOrder'
      ].join('/'),
      requestObject,
      {
        headers: {
          'Authorization': `Bearer ${this.browserLocalStorage.getItem('accessToken')}`
        }
      }
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
