import {Component, Input, OnInit} from '@angular/core';
import {OrderInterface} from "../../api/interfaces/order.interface";
import {ShowBasketModalWindowComponent} from "../show-basket-modal-window/show-basket-modal-window.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {OrderedProductsModalWindowComponent} from "../ordered-products-modal-window/ordered-products-modal-window.component";

@Component({
  selector: 'app-user-order',
  templateUrl: './user-order.component.html',
  styleUrls: ['./user-order.component.scss']
})
export class UserOrderComponent implements OnInit {
  @Input() order: OrderInterface = {
    id: 0,
    date: '',
    price: 0,
    state: ''
  };

  constructor(
    private modalWindowService: NgbModal
  ) { }

  ngOnInit(): void {
  }

  onShowOrderClick() {
    // link to modal window
    const ref = this.modalWindowService.open(OrderedProductsModalWindowComponent, { size: 'xl', scrollable: true } );
    // pass to selected modal window id of this order
    ref.componentInstance.orderId = this.order.id;
  }
}
