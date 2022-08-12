import {Component, Input, OnInit} from '@angular/core';
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ShowProductModalWindowComponent} from "../show-product-modal-window/show-product-modal-window.component";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  @Input() product: ShortProductInfoInterface = {
    id: 0,
    name: '',
    price: 0,
    weight: 0,
    weightMeasurement: '',
    discount: 0,
    photoPath: ''
  };

  constructor(
    private readonly router: Router,
    private modalWindowService: NgbModal
  ) { }

  ngOnInit(): void {}

  onShowProductFullInfoClick() {
    // link to modal window
    const ref = this.modalWindowService.open(ShowProductModalWindowComponent, { size: 'xl' });
    // pass to selected modal window id of product (we can also pass the object)
    ref.componentInstance.productId = this.product.id;
  }
}


