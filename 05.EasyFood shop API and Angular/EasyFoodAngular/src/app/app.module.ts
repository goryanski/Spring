import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ComponentsModule} from "./components/components.module";
import {ApiServicesModule} from "./api/services-api.module";
import {HttpClientModule} from "@angular/common/http";
import {environment} from "../environments/environment";
import {AppEnvironment} from "./shared/app-environment.interface";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ComponentsModule,
    ApiServicesModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: AppEnvironment,
      useValue: environment
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }