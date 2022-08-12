import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {ComponentsModule} from "../../components/components.module";


@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    NgbPaginationModule,
    ComponentsModule
  ]
})
export class HomeModule { }
