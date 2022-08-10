import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import {RouterModule} from "@angular/router";
import { ProductsListComponent } from './products-list/products-list.component';
import { ProductComponent } from './product/product.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

// common components
@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    ProductsListComponent,
    ProductComponent
  ],
    exports: [
        HeaderComponent,
        FooterComponent,
        ProductComponent
    ],
  imports: [
    CommonModule,
    RouterModule,
    NgbModule
  ]
})

export class ComponentsModule { }
