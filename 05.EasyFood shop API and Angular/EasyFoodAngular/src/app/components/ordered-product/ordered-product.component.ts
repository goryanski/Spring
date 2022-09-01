import {Component, Input, OnInit} from '@angular/core';
import {OrderedProductInterface} from "../../api/interfaces/ordered-product.interface";
import {ShowProductModalWindowComponent} from "../show-product-modal-window/show-product-modal-window.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-ordered-product',
  templateUrl: './ordered-product.component.html',
  styleUrls: ['./ordered-product.component.scss']
})
export class OrderedProductComponent implements OnInit {
  @Input() product: OrderedProductInterface = {
    originalProductId: 0,
    name: '',
    count: 0,
    price: 0
  };

  constructor(
    private modalWindowService: NgbModal
  ) { }

  ngOnInit(): void {
  }

  onShowProductClick() {
    // link to modal window
    const ref = this.modalWindowService.open(ShowProductModalWindowComponent, { size: 'xl' });
    // pass to selected modal window id of product
    ref.componentInstance.productId = this.product.originalProductId;
  }
}
