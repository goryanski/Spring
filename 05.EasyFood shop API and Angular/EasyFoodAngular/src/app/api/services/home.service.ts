import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppEnvironment} from "../../shared/app-environment.interface";
import {Observable, publishReplay, refCount} from "rxjs";
import {CategoryInterface} from "../interfaces/category.interface";

@Injectable()
export class HomeService {
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
}
