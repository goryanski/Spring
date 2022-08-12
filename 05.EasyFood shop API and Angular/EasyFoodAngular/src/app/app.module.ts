import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ComponentsModule} from "./components/components.module";
import {ApiServicesModule} from "./api/services-api.module";
import {HttpClientModule} from "@angular/common/http";
import {environment} from "../environments/environment";
import {AppEnvironment} from "./shared/app-environment.interface";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {BrowserLocalStorage} from "./shared/storage/local-storage";

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
    {
      provide: AppEnvironment,
      useValue: environment
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
