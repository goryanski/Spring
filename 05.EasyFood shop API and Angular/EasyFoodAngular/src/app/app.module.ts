import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ComponentsModule} from "./components/components.module";
import {ApiServicesModule} from "./api/services-api.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {environment} from "../environments/environment";
import {AppEnvironment} from "./shared/app-environment.interface";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {BrowserLocalStorage} from "./shared/storage/local-storage";
import {AuthHelper} from "./shared/helpers/auth-helper";
import {QueryHttpInterceptor} from "./shared/http-interceptors/query-http.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ComponentsModule,
    ApiServicesModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [
    BrowserLocalStorage,
    AuthHelper,
    {
      provide: AppEnvironment,
      useValue: environment
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: QueryHttpInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
