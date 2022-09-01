import {Component, Input, OnInit} from '@angular/core';
import {BrowserLocalStorage} from "../../../shared/storage/local-storage";
import {OrdersService} from "../../../api/services/orders.service";
import {OrderInterface} from "../../../api/interfaces/order.interface";
import {take} from "rxjs";

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.scss']
})
export class UserOrdersComponent implements OnInit {
  orders: OrderInterface[] = [];
  isOrdersListEmpty: boolean = false;

  constructor(
    private readonly localStorage: BrowserLocalStorage,
    private readonly ordersService: OrdersService
  ) { }

  ngOnInit(): void {
    this.ordersService.getUserOrders(this.localStorage.getCurrentUserId())
      .pipe(take(1))
      .subscribe(response => {
        this.orders = response;
        this.isOrdersListEmpty = response.length == 0;
      });
  }
}
