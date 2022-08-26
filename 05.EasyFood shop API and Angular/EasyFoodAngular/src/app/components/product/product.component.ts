import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ShowProductModalWindowComponent} from "../show-product-modal-window/show-product-modal-window.component";
import {AuthHelper} from "../../shared/helpers/auth-helper";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";

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
  isUserAuthenticated: boolean;
  isProductOrdered: boolean = false;
  productCountValue: number = 1
  //@ViewChild() -- use this

  constructor(
    private readonly router: Router,
    private modalWindowService: NgbModal,
    private readonly localStorage: BrowserLocalStorage
  ) {
    this.isUserAuthenticated = localStorage.isUserAuthenticated();
  }

  ngOnInit(): void {}

  onShowProductFullInfoClick() {
    // link to modal window
    const ref = this.modalWindowService.open(ShowProductModalWindowComponent, { size: 'xl' });
    // pass to selected modal window id of product (we can also pass the object)
    ref.componentInstance.productId = this.product.id;
  }

  OnBuyProductClock() {
    this.isProductOrdered = true;

  }
}


