import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {BasketProductRequestInterface} from "../interfaces/requests/basket-product-request.interface";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {RemoveBasketProductRequestInterface} from "../interfaces/requests/remove-basket-product-request.interface";
import {BasketResponseInterface} from "../interfaces/responses/basket-response.interface";
import {UpdateBasketProductRequestInterface} from "../interfaces/requests/update-basket-product-request.interface";

@Injectable()
export class BasketService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly browserLocalStorage: BrowserLocalStorage
  ) {}

  addProductToBasket(requestObject: BasketProductRequestInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'basket',
        'addToBasket'
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

  removeProductFromBasket(requestObject: RemoveBasketProductRequestInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'basket',
        'removeFromBasket'
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

  getUserProductsCountInBasket(userId: number): Observable<number> {
    return this.httpClient.get<number>(
      [
        this.appEnv.apiEasyFoodURL,
        'basket',
        'countProductsInBasket',
        `${userId}`
      ].join('/'),
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

  getAllBasketProducts(userId: number): Observable<BasketResponseInterface> {
    return this.httpClient.get<BasketResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'basket',
        'getProducts',
        `${userId}`
      ].join('/'),
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

  updateBasketProduct(requestObject: UpdateBasketProductRequestInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'basket',
        'updateProduct'
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

  deleteAllProducts(userId: number): Observable<number> {
    return this.httpClient.delete<number>(
      [
        this.appEnv.apiEasyFoodURL,
        'basket',
        'deleteAll',
        `${userId}`
      ].join('/'),
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
