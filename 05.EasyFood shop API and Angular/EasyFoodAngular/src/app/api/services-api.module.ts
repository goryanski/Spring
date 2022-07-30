import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {HomeService} from "./services/home.service";

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule
  ],
  providers: [
    HomeService
  ]
})
export class ApiServicesModule { }
