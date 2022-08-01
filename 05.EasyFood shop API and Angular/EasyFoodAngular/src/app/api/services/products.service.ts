import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {ShortProductInfoInterface} from "../interfaces/short-product-info.interface";

@Injectable()
export class ProductsService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {}

  getProducts(categoryId: number): Observable<ShortProductInfoInterface[]> {
    return this.httpClient.get<ShortProductInfoInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'products',
        `${categoryId}`
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}

