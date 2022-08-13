import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsFilterRoutingModule } from './products-filter-routing.module';
import { ProductsFilterComponent } from './products-filter.component';
import {ComponentsModule} from "../../components/components.module";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";


@NgModule({
  declarations: [
    ProductsFilterComponent
  ],
  imports: [
    CommonModule,
    ProductsFilterRoutingModule,
    ComponentsModule,
    NgbDropdownModule
  ]
})
export class ProductsFilterModule { }
