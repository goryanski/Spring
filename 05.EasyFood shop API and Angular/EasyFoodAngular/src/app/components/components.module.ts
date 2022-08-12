import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import {RouterModule} from "@angular/router";
import { ProductComponent } from './product/product.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ShowProductModalWindowComponent } from './show-product-modal-window/show-product-modal-window.component';

// common components
@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    ProductComponent,
    ShowProductModalWindowComponent
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
