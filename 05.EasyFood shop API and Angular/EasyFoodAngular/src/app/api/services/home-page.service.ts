import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {CategoryInterface} from "../interfaces/category.interface";
import {ProductsByCategoryIdRequestInterface} from "../interfaces/requests/products-by-category-id-request.interface";
import {PaginatedProductsResponseInterface} from "../interfaces/responses/paginated-products-response.interface";

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

  getProductsByCategoryId(requestObject: ProductsByCategoryIdRequestInterface)
          : Observable<PaginatedProductsResponseInterface> {
    return this.httpClient.post<PaginatedProductsResponseInterface>(
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
