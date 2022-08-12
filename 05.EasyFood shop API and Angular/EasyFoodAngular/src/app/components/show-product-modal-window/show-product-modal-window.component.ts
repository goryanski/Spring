import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {FullProductInfoInterface} from "../../api/interfaces/full-product-info.interface";
import {Observable, take} from "rxjs";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {ShowProductWindowService} from "../../api/services/show-product-window.service";

@Component({
  selector: 'app-show-product-modal-window',
  templateUrl: './show-product-modal-window.component.html',
  styleUrls: ['./show-product-modal-window.component.scss']
})
export class ShowProductModalWindowComponent implements OnInit {
  // input param (ref.componentInstance.productId in product.component.ts)
  productId: number = 0;
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
    public modal: NgbActiveModal,
    private readonly productsService: ShowProductWindowService
  ) { }

  ngOnInit(): void {
    this.productsService.getFullInfoProductById(this.productId)
      .pipe(take(1))
      .subscribe(res => {
        this.product = res;
        this.products$ = this.productsService.getSimilarProducts(this.product.categoryId, this.similarProductsCount);
      });
  }

}
