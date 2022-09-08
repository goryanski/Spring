import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {EditProductInterface} from "../interfaces/edit-product.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {ProductLinkedDataResponseInterface} from "../interfaces/responses/product-linked-data-response.interface";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {RunOutProductsRequestInterface} from "../interfaces/requests/run-out-products-request.interface";
import {PaginatedProductsResponseInterface} from "../interfaces/responses/paginated-products-response.interface";

@Injectable()
export class AdministrateProductsService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly localStorage: BrowserLocalStorage
  ) {
  }

  getProductToEdit(productId: number): Observable<EditProductInterface> {
    return this.httpClient.get<EditProductInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'edit',
        `${productId}`
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

  getProductLinkedData(): Observable<ProductLinkedDataResponseInterface> {
    return this.httpClient.get<ProductLinkedDataResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'productLinkedData',
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

  editProduct(product: EditProductInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.put<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'editProduct',
      ].join('/'),
      product,
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

  addProduct(product: EditProductInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'addProduct',
      ].join('/'),
      product,
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

  getRunOutProducts(requestObj: RunOutProductsRequestInterface)
    : Observable<PaginatedProductsResponseInterface> {
    return this.httpClient.post<PaginatedProductsResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'runOutProducts',
      ].join('/'),
      requestObj,
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
