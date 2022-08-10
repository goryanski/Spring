import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {HomePageService} from "./services/home-page.service";
import {ShowProductPageService} from "./services/show-product-page.service";

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule
  ],
  providers: [
    HomePageService,
    ShowProductPageService
  ]
})
export class ApiServicesModule { }
