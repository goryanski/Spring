import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EditProductRoutingModule } from './edit-product-routing.module';
import { EditProductComponent } from './edit-product.component';
import {ReactiveFormsModule} from "@angular/forms";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";
import {ComponentsModule} from "../../components/components.module";


@NgModule({
  declarations: [
    EditProductComponent
  ],
    imports: [
        CommonModule,
        EditProductRoutingModule,
        ReactiveFormsModule,
        NgbDropdownModule,
        ComponentsModule
    ]
})
export class EditProductModule { }
