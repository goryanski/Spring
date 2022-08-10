import { Component, OnInit } from '@angular/core';
import {FullProductInfoInterface} from "../../api/interfaces/full-product-info.interface";
import {Observable, take} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {ShowProductPageService} from "../../api/services/show-product-page.service";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";

@Component({
  selector: 'app-show-product',
  templateUrl: './show-product.component.html',
  styleUrls: ['./show-product.component.scss']
})
export class ShowProductComponent implements OnInit {
  product: FullProductInfoInterface = {
    id: 0,
    name: '',
    price: 0,
    weight: 0,
    weightMeasurement: '',
    discount: 0,
    photoPath: '',
    description: '',
    likesCount: 0,
    brand: '',
    country: '',
    categoryId: 0
  };
  readonly similarProductsCount: number = 5;
  products$?: Observable<ShortProductInfoInterface[]>;

  constructor(
    private readonly productsService: ShowProductPageService,
    private readonly activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      // get product id from route params
      let productId: number = Number(params['id']);
      this.productsService.getFullInfoProductById(productId)
        .pipe(take(1))
        .subscribe(res => {
          this.product = res;
          this.products$ = this.productsService.getSimilarProducts(this.product.categoryId, this.similarProductsCount);
        });
    });
  }
}
