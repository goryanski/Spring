import { Component, OnInit } from '@angular/core';
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {OrderInterface} from "../../api/interfaces/order.interface";
import {OrdersService} from "../../api/services/orders.service";
import {take} from "rxjs";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  myOrdersChangeNumber: number = 0;
  favoriteProductsChangeNumber: number = 0;

  constructor() { }
  ngOnInit(): void {}

  onMyOrdersClick() {
    this.myOrdersChangeNumber = this.randomNumberGenerator();
  }

  onFavoriteProductsClick() {
    this.favoriteProductsChangeNumber = this.randomNumberGenerator();
  }

  randomNumberGenerator(): number {
    let min: number = 1;
    let max: number = 999999;
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }
}
