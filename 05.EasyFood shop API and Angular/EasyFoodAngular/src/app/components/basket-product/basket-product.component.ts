import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BasketProductInterface} from "../../api/interfaces/basket-product.interface";
import {RemoveBasketProductRequestInterface} from "../../api/interfaces/requests/remove-basket-product-request.interface";
import {take} from "rxjs";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {BasketService} from "../../api/services/basket.service";
import {AuthHelper} from "../../shared/helpers/auth-helper";
import {UpdateBasketProductRequestInterface} from "../../api/interfaces/requests/update-basket-product-request.interface";

@Component({
  selector: 'app-basket-product',
  templateUrl: './basket-product.component.html',
  styleUrls: ['./basket-product.component.scss']
})
export class BasketProductComponent implements OnInit {
  @Input() product: BasketProductInterface = {
    productId: 0,
    userId: 0,
    name: '',
    photoPath: '',
    weight: 0,
    weightMeasurement: '',
    weightFlexible: false,
    pricePerOneItem: 0,
    generalCount: 0,
    generalPrice: 0,
    countInStorage: 0
  };

  @Output() productRemovingEvent = new EventEmitter<RemoveBasketProductRequestInterface>();
  @Output() productPriceChangedEvent = new EventEmitter();

  isErrorCountMessage: boolean = false;


  constructor(
    private readonly localStorage: BrowserLocalStorage,
    private readonly basketService: BasketService,
    private readonly authHelper: AuthHelper
  ) {}

  ngOnInit(): void {
    if(this.product.generalCount > this.product.countInStorage) {
      this.product.generalCount = this.product.countInStorage;
      // set timeout because basketPrice field in the parent component is not initialized yet, we have to wait for a little
      setTimeout(() => {
        this.calculateNewPrice();
        this.updateProductInDb();
      },500);
    }
  }

  onReduceProductCountValueClick(value: string) {
    // set new count to count input
    let countValue: number = 0;
    if(!this.product.weightFlexible) {
      countValue = parseInt(value);
      if(countValue >= 2) {
        this.product.generalCount = countValue - 1;
      } else {
        this.product.generalCount = 1;
      }
    } else {
      countValue = parseFloat(value);
      if(countValue >= 0.5) {
        this.product.generalCount = parseFloat((countValue - 0.2).toFixed(1)); // one digit after point
      }  else {
        this.product.generalCount = 0.3;
      }
    }
    this.isErrorCountMessage = this.product.generalCount > this.product.countInStorage;

    this.calculateNewPrice();
    this.updateProductInDb();
  }

  onIncreaseProductCountValueClick(value: string) {
    // the same logic as in onReduceProductCountValueClick(), but increase count value
    let countValue: number = 0;
    if(!this.product.weightFlexible) {
      countValue = parseInt(value);
      if(countValue < 100) {
        this.product.generalCount = countValue + 1;
      } else {
        this.product.generalCount = 100;
      }
    } else {
      countValue = parseFloat(value);
      if(countValue > 99.5) {
        this.product.generalCount = 100;
      } else {
        this.product.generalCount = countValue + 0.5;
      }
    }

    if(this.product.generalCount > this.product.countInStorage) {
      this.product.generalCount = this.product.countInStorage;
      this.isErrorCountMessage = true;
    } else {
      this.isErrorCountMessage = false;
      this.calculateNewPrice();
      this.updateProductInDb();
    }
  }

  onDeleteProductFromBasketClick() {
    // remove from DB (we already did the same in product.component.ts)
    let requestObject: RemoveBasketProductRequestInterface = this.getRequestObjectToRemove();
    this.basketService.removeProductFromBasket(requestObject)
      .pipe(take(1))
      .subscribe(response => {
        if(response.message === 'removed') {
          this.authHelper.reduceBasketProductsCount();

          // delete from list on client side (in ShowBasketModalWindowComponent - parent component)
          this.productRemovingEvent.emit(requestObject); // we send there requestObject because we don't need to send whole this.product object - we need only 2 params
        }
      });
  }

  private calculateNewPrice() {
    // calculate new price for this product
    let generalPrice: number = this.product.pricePerOneItem * this.product.generalCount;
    this.product.generalPrice = parseFloat(generalPrice.toFixed(2));

    // calculate new general price for all basket products (in ShowBasketModalWindowComponent - parent component)
    this.productPriceChangedEvent.emit();
  }

  private updateProductInDb() {
    let requestObject: UpdateBasketProductRequestInterface = this.getRequestObjectToUpdate();
    this.basketService.updateBasketProduct(requestObject)
      .pipe(take(1))
      .subscribe();
  }

  private getRequestObjectToRemove(): RemoveBasketProductRequestInterface {
    return {
      userId: this.product.userId,
      productId: this.product.productId
    };
  }

  private getRequestObjectToUpdate(): UpdateBasketProductRequestInterface {
    return {
      userId: this.product.userId,
      productId: this.product.productId,
      generalCount: this.product.generalCount,
      generalPrice: this.product.generalPrice
    };
  }
}
