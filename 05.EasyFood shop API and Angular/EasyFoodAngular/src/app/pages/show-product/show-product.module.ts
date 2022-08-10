import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShowProductRoutingModule } from './show-product-routing.module';
import { ShowProductComponent } from './show-product.component';
import {ComponentsModule} from "../../components/components.module";


@NgModule({
  declarations: [
    ShowProductComponent
  ],
  imports: [
    CommonModule,
    ShowProductRoutingModule,
    ComponentsModule
  ]
})
export class ShowProductModule { }
