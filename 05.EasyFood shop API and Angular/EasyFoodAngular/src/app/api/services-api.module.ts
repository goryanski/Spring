import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {HomePageService} from "./services/home-page.service";
import {ShowProductWindowService} from "./services/show-product-window.service";
import {SearchProductsService} from "./services/search-products.service";
import {ProductsFilterService} from "./services/products-filter.service";
import {AuthService} from "./services/auth.service";

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule
  ],
  providers: [
    HomePageService,
    ShowProductWindowService,
    SearchProductsService,
    ProductsFilterService,
    AuthService
  ]
})
export class ApiServicesModule { }
