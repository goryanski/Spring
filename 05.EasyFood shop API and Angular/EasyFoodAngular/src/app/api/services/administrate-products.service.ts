import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {ShortProductInfoInterface} from "../interfaces/short-product-info.interface";
import {FullProductInfoInterface} from "../interfaces/full-product-info.interface";
import {EditProductInterface} from "../interfaces/edit-product.interface";

@Injectable()
export class AdministrateProductsService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {
  }

  getProductToEdit(productId: number): Observable<EditProductInterface> {
    return this.httpClient.get<EditProductInterface>(
      [
        this.appEnv.apiEasyFoodURL,
        'admins',
        'edit',
        `${productId}`
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
