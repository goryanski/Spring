import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {MessageResponseInterface} from "../interfaces/responses/message-response.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {FavoriteProductRequestInterface} from "../interfaces/requests/favorite-product-request.interface";
import {CategoryInterface} from "../interfaces/category.interface";
import {FavoriteProductsByCategoryIdRequestInterface} from "../interfaces/requests/favorite-products-by-category-id-request.interface";
import {PaginatedProductsResponseInterface} from "../interfaces/responses/paginated-products-response.interface";

@Injectable()
export class FavoritesProductsService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
    private readonly localStorage: BrowserLocalStorage
  ) {}


  isProductFavorite(requestObject: FavoriteProductRequestInterface): Observable<boolean> {
    return this.httpClient.post<boolean>(
      [
        this.appEnv.apiEasyFoodURL,
        'favouritesProducts',
        'check'
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


  deleteProductFromFavorites(requestObject: FavoriteProductRequestInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'favouritesProducts',
        'delete'
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

  addProductToFavorites(requestObject: FavoriteProductRequestInterface)
    : Observable<MessageResponseInterface> {
    return this.httpClient.post<MessageResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'favouritesProducts',
        'add'
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

  getFavoritesProductsCategories(userId: number): Observable<CategoryInterface[]> {
    return this.httpClient.get<CategoryInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'favouritesProducts',
        'categories',
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

  getFavoriteProductsByCategoryId(requestObject: FavoriteProductsByCategoryIdRequestInterface)
    : Observable<PaginatedProductsResponseInterface> {
    return this.httpClient.post<PaginatedProductsResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'favouritesProducts',
        'getProducts'
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
}
