import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {CategoryInterface} from "../interfaces/category.interface";
import {ShortProductInfoInterface} from "../interfaces/short-product-info.interface";

@Injectable()
export class HomePageService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {}

  getAllCategories(): Observable<CategoryInterface[]> {
    return this.httpClient.get<CategoryInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'home'
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }

  getAllProductsCountByCategoryId(categoryId: number): Observable<number> {
    return this.httpClient.get<number> (
      [
        this.appEnv.apiEasyFoodURL,
        'products',
        'count',
        `${categoryId}`
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }

  getProductsByCategoryId(requestObject: object): Observable<ShortProductInfoInterface[]> {
    return this.httpClient.post<ShortProductInfoInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'products',
        'byCategoryId'
      ].join('/'),
      requestObject
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
