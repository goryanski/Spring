import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserProfileRoutingModule } from './user-profile-routing.module';
import { UserProfileComponent } from './user-profile.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { UserFavoriteProductsComponent } from './user-favorite-products/user-favorite-products.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';
import {UserOrderComponent} from "../../components/user-order/user-order.component";
import {OrderedProductsModalWindowComponent} from "../../components/ordered-products-modal-window/ordered-products-modal-window.component";
import {ComponentsModule} from "../../components/components.module";
import { UserProfileCardComponent } from './user-profile-card/user-profile-card.component';
import {ReactiveFormsModule} from "@angular/forms";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";


@NgModule({
  declarations: [
    UserProfileComponent,
    EditProfileComponent,
    UserFavoriteProductsComponent,
    UserOrdersComponent,
    UserOrderComponent,
    OrderedProductsModalWindowComponent,
    UserProfileCardComponent
  ],
  imports: [
    CommonModule,
    UserProfileRoutingModule,
    ComponentsModule,
    ReactiveFormsModule,
    NgbPaginationModule
  ],
  exports: [
    UserOrderComponent,
    OrderedProductsModalWindowComponent,
    UserProfileCardComponent
  ]
})
export class UserProfileModule { }
