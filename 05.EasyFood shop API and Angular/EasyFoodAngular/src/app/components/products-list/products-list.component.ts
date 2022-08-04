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
  products$?: Observable<ShortProductInfoInterface[]>;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly productsService: ProductsService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      // get category id from route params
      let selectedCategoryId: number = Number(params['categoryId']);
      this.products$ = this.productsService.getProducts(selectedCategoryId);
    });

  }
}
