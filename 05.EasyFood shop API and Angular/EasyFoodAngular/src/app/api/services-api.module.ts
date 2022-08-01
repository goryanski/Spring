import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {HomeService} from "./services/home.service";
import {ProductsService} from "./services/products.service";

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule
  ],
  providers: [
    HomeService,
    ProductsService
  ]
})
export class ApiServicesModule { }
