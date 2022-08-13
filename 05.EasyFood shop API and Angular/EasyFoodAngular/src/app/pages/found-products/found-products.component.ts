import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {ProductsByNameRequestInterface} from "../../api/interfaces/requests/products-by-name-request.interface";
import {SearchProductsService} from "../../api/services/search-products.service";
import {take} from "rxjs";

@Component({
  selector: 'app-found-products',
  templateUrl: './found-products.component.html',
  styleUrls: ['./found-products.component.scss']
})
export class FoundProductsComponent implements OnInit {
  searchKey: string = '';
  products: ShortProductInfoInterface[] = [];
  readonly pageSize: number = 5;
  currentPage: number = 1;
  allProductsCount: number = 0;
  isProductsListEmpty: boolean = false;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly searchProductsService: SearchProductsService,
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.searchKey = params['name']; // name - name of param from app-routing.module
      this.currentPage = 1;
      this.getProducts();
    });
  }

  getNextProducts() {
    this.getProducts();
  }

  getProducts() {
    let requestObj: ProductsByNameRequestInterface = this.getRequestObject();
    this.searchProductsService.getProductsBySubstringOfName(requestObj)
      .pipe(take(1))
      .subscribe(response => {
        this.allProductsCount = response.totalItems;
        if(this.allProductsCount == 0) {
          this.isProductsListEmpty = true;
        }
        this.products = response.products;
      });
  }

  getRequestObject(): ProductsByNameRequestInterface {
    return {
      name: this.searchKey,
      currentPage: this.currentPage - 1,
      pageSize: this.pageSize
    };
  }
}
