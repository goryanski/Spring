import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductsService} from "../../api/services/products.service";
import {Observable} from "rxjs";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {CategoryInterface} from "../../api/interfaces/category.interface";

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent implements OnInit {
  //selectedCategoryId: number = 0
  products$?: Observable<ShortProductInfoInterface[]>;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly productsService: ProductsService
  ) {

  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      let selectedCategoryId: number = Number(params['categoryId']);
      //console.log('products-list selectedCategoryId: ' + selectedCategoryId);
      this.products$ = this.productsService.getProducts(selectedCategoryId);
      this.products$.subscribe(res => console.log(...res));
    });

  }
}
