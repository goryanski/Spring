import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import {RouterModule} from "@angular/router";
import { ProductsListComponent } from './products-list/products-list.component';


// common components
@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    ProductsListComponent
  ],
  exports: [
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})

export class ComponentsModule { }
