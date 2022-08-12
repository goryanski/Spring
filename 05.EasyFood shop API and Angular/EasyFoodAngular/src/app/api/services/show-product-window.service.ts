import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {ShortProductInfoInterface} from "../interfaces/short-product-info.interface";
import {FullProductInfoInterface} from "../interfaces/full-product-info.interface";

@Injectable()
export class ShowProductWindowService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {}

  getFullInfoProductById(productId: number): Observable<FullProductInfoInterface> {
    return this.httpClient.get<FullProductInfoInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'products',
        `${productId}`
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }

  getSimilarProducts(categoryId: number, count: number): Observable<ShortProductInfoInterface[]> {
    return this.httpClient.get<ShortProductInfoInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'products',
        'similar',
        `${categoryId}`,
        `${count}`
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}

