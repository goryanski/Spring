import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {CategoryInterface} from "../interfaces/category.interface";
import {CountryInterface} from "../interfaces/country.interface";
import {BrandInterface} from "../interfaces/brand.interface";


@Injectable()
export class ProductsFilterService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly appEnv: AppEnvironment,
  ) {}

  getAllCountries(): Observable<CountryInterface[]> {
    return this.httpClient.get<CountryInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'products-filter',
        'countries'
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }

  getAllBrands(): Observable<BrandInterface[]> {
    return this.httpClient.get<BrandInterface[]>(
      [
        this.appEnv.apiEasyFoodURL,
        'products-filter',
        'brands'
      ].join('/')
    ).pipe(
      publishReplay(1),
      refCount()
    );
  }
}
