import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsFilterComponent} from "./products-filter.component";

const routes: Routes = [
  {
    path: '',
    component: ProductsFilterComponent
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsFilterRoutingModule { }
