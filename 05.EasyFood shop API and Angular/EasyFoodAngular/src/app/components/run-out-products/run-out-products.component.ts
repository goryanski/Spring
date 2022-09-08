import { Component, OnInit } from '@angular/core';
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {ActivatedRoute} from "@angular/router";
import {SearchProductsService} from "../../api/services/search-products.service";
import {ViewportScroller} from "@angular/common";
import {ProductsByNameRequestInterface} from "../../api/interfaces/requests/products-by-name-request.interface";
import {take} from "rxjs";
import {RunOutProductsRequestInterface} from "../../api/interfaces/requests/run-out-products-request.interface";
import {AdministrateProductsService} from "../../api/services/administrate-products.service";

@Component({
  selector: 'app-run-out-products',
  templateUrl: './run-out-products.component.html',
  styleUrls: ['./run-out-products.component.scss']
})
export class RunOutProductsComponent implements OnInit {
  products: ShortProductInfoInterface[] = [];
  readonly pageSize: number = 10;
  currentPage: number = 1;
  allProductsCount: number = 0;
  isProductsListEmpty: boolean = false;
  leftInStockLimit: number = 50;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly scroll: ViewportScroller,
    private readonly administrateProductsService: AdministrateProductsService
  ) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getNextProducts() {
    this.getProducts();
    this.scroll.scrollToPosition([0,0]);
  }

  getProducts() {
    let requestObj: RunOutProductsRequestInterface = this.getRequestObject();
    this.administrateProductsService.getRunOutProducts(requestObj)
      .pipe(take(1))
      .subscribe(response => {
        this.allProductsCount = response.totalItems;
        this.isProductsListEmpty = this.allProductsCount == 0;
        this.products = response.products;
      });
  }

  getRequestObject(): RunOutProductsRequestInterface {
    return {
      leftInStockLimit: this.leftInStockLimit,
      currentPage: this.currentPage - 1,
      pageSize: this.pageSize
    };
  }

}
