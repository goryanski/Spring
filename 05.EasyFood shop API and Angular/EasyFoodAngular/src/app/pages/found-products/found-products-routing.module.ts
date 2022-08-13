import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FoundProductsComponent} from "./found-products.component";

const routes: Routes = [
  {
    path: '',
    component: FoundProductsComponent
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FoundProductsRoutingModule { }
