import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {OrdersService} from "../../api/services/orders.service";
import {take} from "rxjs";
import {OrderedProductInterface} from "../../api/interfaces/ordered-product.interface";

@Component({
  selector: 'app-ordered-products-modal-window',
  templateUrl: './ordered-products-modal-window.component.html',
  styleUrls: ['./ordered-products-modal-window.component.scss']
})
export class OrderedProductsModalWindowComponent implements OnInit {
  // input param (ref.componentInstance.orderId in user-order.component.ts)
  orderId: number = 0;
  isProductsListEmpty: boolean = false;
  products: OrderedProductInterface[] = [];

  constructor(
    public modal: NgbActiveModal,
    private readonly ordersService: OrdersService
  ) { }

  ngOnInit(): void {
    this.ordersService.getOrderProducts(this.orderId)
      .pipe(take(1))
      .subscribe(response => {
        this.products = response;
        this.isProductsListEmpty = response.length == 0;
      });
  }

}
