import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FoundProductsRoutingModule } from './found-products-routing.module';
import { FoundProductsComponent } from './found-products.component';
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {ComponentsModule} from "../../components/components.module";


@NgModule({
  declarations: [
    FoundProductsComponent
  ],
  imports: [
    CommonModule,
    FoundProductsRoutingModule,
    NgbPaginationModule,
    ComponentsModule
  ]
})
export class FoundProductsModule { }
