import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {ShortProductInfoInterface} from "../interfaces/short-product-info.interface";
import {ProductsByNameRequestInterface} from "../interfaces/requests/products-by-name-request.interface";
import {ProductsByCategoryIdRequestInterface} from "../interfaces/requests/products-by-category-id-request.interface";
import {PaginatedProductsResponseInterface} from "../interfaces/responses/paginated-products-response.interface";

@Injectable()
export class SearchProductsService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {}

  getProductsBySubstringOfName(requestObject: ProductsByNameRequestInterface)
    : Observable<PaginatedProductsResponseInterface> {
    return this.httpClient.post<PaginatedProductsResponseInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'products',
        'byName'
      ].join('/'),
      requestObject
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
