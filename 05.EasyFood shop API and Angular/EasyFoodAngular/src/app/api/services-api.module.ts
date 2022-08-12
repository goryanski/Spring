import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {HomePageService} from "./services/home-page.service";
import {ShowProductWindowService} from "./services/show-product-window.service";

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule
  ],
  providers: [
    HomePageService,
    ShowProductWindowService
  ]
})
export class ApiServicesModule { }
