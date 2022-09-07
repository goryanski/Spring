import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminProfileRoutingModule } from './admin-profile-routing.module';
import { AdminProfileComponent } from './admin-profile.component';
import {UserProfileModule} from "../user-profile/user-profile.module";
import {ComponentsModule} from "../../components/components.module";


@NgModule({
  declarations: [
    AdminProfileComponent
  ],
    imports: [
        CommonModule,
        AdminProfileRoutingModule,
        UserProfileModule,
        ComponentsModule
    ],
  exports: []
})
export class AdminProfileModule { }
