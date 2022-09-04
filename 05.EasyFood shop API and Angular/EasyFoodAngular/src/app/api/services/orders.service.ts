import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {MakeOrderRequestInterface} from "../interfaces/requests/make-order-request.interface";
import {OrderInterface} from "../interfaces/order.interface";
import {OrderedProductInterface} from "../interfaces/ordered-product.interface";
import {OrdersRequestInterface} from "../interfaces/requests/orders-request.interface";

@Injectable()
export class OrdersService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly localStorage: BrowserLocalStorage
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
          'Authorization': `Bearer ${this.localStorage.getItem('accessToken')}`
        }
      }
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }

  getUserOrders(requestObject: OrdersRequestInterface) : Observable<OrderInterface[]> {
    return this.httpClient.post<OrderInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'orders',
        'userOrders'
      ].join('/'),
      requestObject,
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

  getOrderProducts(orderId: number): Observable<OrderedProductInterface[]> {
    return this.httpClient.get<OrderedProductInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'orders',
        'orderedProducts',
        `${orderId}`
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
}
