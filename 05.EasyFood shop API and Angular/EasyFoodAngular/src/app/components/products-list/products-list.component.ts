import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductsService} from "../../api/services/products.service";
import {Observable} from "rxjs";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent implements OnInit {
  products$?: Observable<ShortProductInfoInterface[]>;
  readonly pageSize: number = 4;
  currentPage: number = 1; // changes every time we click on pagination panel
  allProductsCount: number = 0;
  selectedCategoryId: number = 0;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly productsService: ProductsService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      // get category id from route params
      this.selectedCategoryId = Number(params['categoryId']);
      // get allProductsCount first to display it on pagination panel
      this.productsService.getAllProductsCountByCategoryId(this.selectedCategoryId)
        .subscribe(count => this.allProductsCount = count);

      // get first page of products
      this.getProducts();
    });

  }

  getNextProducts() {
    this.getProducts();
  }

  getProducts() {
    let requestObj = this.getRequestObject();
    this.products$ = this.productsService.getProductsByCategoryId(requestObj);
  }

  getRequestObject(): object {
    // calculate skip for pagination
    let skip: number;
    if(this.currentPage == 1) {
      skip = 0;
    } else {
      skip = (this.currentPage - 1) * this.pageSize;
    }

    // define RequestObject
    return {
      'categoryId': this.selectedCategoryId,
      'skip': skip,
      'limit': this.pageSize
    };
  }


}
