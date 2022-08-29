import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ShowProductModalWindowComponent} from "../show-product-modal-window/show-product-modal-window.component";
import {AuthHelper} from "../../shared/helpers/auth-helper";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BasketProductRequestInterface} from "../../api/interfaces/requests/basket-product-request.interface";
import {take} from "rxjs";
import {BasketService} from "../../api/services/basket-service";
import {RemoveBasketProductRequestInterface} from "../../api/interfaces/requests/remove-basket-product-request.interface";

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
    photoPath: '',
    weightFlexible: false
  };
  isUserAuthenticated: boolean;
  isProductOrdered: boolean = false;

  isInvalidCountMessage: boolean = false;
  isSuccessCountMessage: boolean = false;
  invalidCountMessageText: string = '';
  successCountMessageText: string = '';

  constructor(
    private readonly router: Router,
    private modalWindowService: NgbModal,
    private readonly localStorage: BrowserLocalStorage,
    private readonly ordersService: BasketService,
    private readonly authHelper: AuthHelper
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

  onAddToBasketClick(value: string) {
    let invalidMessageText: string = '';

    // field product.weightFlexible – we need it to know how we have to change count of products when user makes order (if it’s wine – we can only add +1 bottle, but if it’s the potatoes – we want to add 1.5 kg or even 700 g)
    if(!this.product.weightFlexible) {
      // allows only whole number (int)
      let intValue: number = Math.round(parseInt(value));
          if(!isNaN(intValue)) {
            if(intValue > 100) {
              invalidMessageText = 'max value is 100';
            } else if(intValue < 1) {
              invalidMessageText = 'min value is 1';
            } else {
              this.addProductToBasket(intValue);
            }
          } else {
            invalidMessageText = 'invalid count value';
          }
    } else {
          // allows also fractional number (float). toFixed(1) - one digit after point
          let floatValue: number = parseFloat(parseFloat(value).toFixed(1));
          if(!isNaN(floatValue)) {
            if(floatValue > 100) {
              invalidMessageText = 'max value is 100';
            } else if(floatValue < 0.5) {
              invalidMessageText = 'min value is 0.5';
            } else {
              this.addProductToBasket(floatValue);
            }
          } else {
            invalidMessageText = 'invalid count value';
          }
    }

    // error handle
    this.showErrorMessage(invalidMessageText);
  }

  private addProductToBasket(countValue: number) {
    let invalidMessageText: string = '';
    let successMessageText: string = '';

    let requestObject: BasketProductRequestInterface = this.getRequestObjectToAdd(countValue);
    this.ordersService.addProductToBasket(requestObject)
      .pipe(take(1))
      .subscribe(response => {
        if(response.message === 'added') {
          this.authHelper.increaseBasketProductsCount();
          successMessageText = 'added count: ' + countValue;
        } else if(response.message === 'updated') {
          successMessageText = 'updated count: ' + countValue;
        } else {
          invalidMessageText = response.message;
        }

        // show result
        this.showErrorMessage(invalidMessageText);
        this.showSuccessMessage(successMessageText);
      });
  }

  onDeleteProductFromBasketClick(value: string) {
    let invalidMessageText: string = '';
    let successMessageText: string = '';

    let requestObject: RemoveBasketProductRequestInterface = this.getRequestObjectToRemove();
    this.ordersService.removeProductFromBasket(requestObject)
      .pipe(take(1))
      .subscribe(response => {
        if(response.message === 'removed') {
          successMessageText = response.message + ' from basket';
          this.authHelper.reduceBasketProductsCount();
        } else {
          invalidMessageText = response.message + ' yet'; // nothing to remove
        }

        // show result
        this.showErrorMessage(invalidMessageText);
        this.showSuccessMessage(successMessageText);
      });
  }


  private getRequestObjectToAdd(productsCount: number): BasketProductRequestInterface {
    return {
      userId: this.localStorage.getCurrentUserId(),
      productId: this.product.id,
      count: productsCount,
      weightFlexible: this.product.weightFlexible
    }
  }
  private getRequestObjectToRemove(): RemoveBasketProductRequestInterface {
    return {
      userId: this.localStorage.getCurrentUserId(),
      productId: this.product.id
    };
  }


  private showErrorMessage(invalidMessageText: string) {
    if(invalidMessageText != '') {
      this.isInvalidCountMessage = true;
      this.invalidCountMessageText = invalidMessageText;
      this.isSuccessCountMessage = false;
    }
  }
  private showSuccessMessage(successMessageText: string) {
    if(successMessageText != '') {
      this.isSuccessCountMessage = true;
      this.successCountMessageText = successMessageText;
      this.isInvalidCountMessage = false;
    }
  }
  // change product count value while ordering product
  // onReduceProductCountValueClick(value: string) {
  //   if(!this.product.weightFlexible) {
  //     let intValue: number = Math.round(parseInt(value));
  //     if(intValue >= 2) {
  //       this.productCountValue = (intValue - 1).toString();
  //     } else {
  //       this.productCountValue = (1).toString();
  //     }
  //   } else {
  //     let floatValue: number = parseFloat(parseFloat(value).toFixed(1)); // one digit after point
  //     if(floatValue >= 1) {
  //       this.productCountValue = (floatValue - 0.5).toString();
  //     } else if(floatValue < 1 && floatValue > 0.5) {
  //       this.productCountValue = floatValue.toString();
  //     } else {
  //       this.productCountValue = (0.5).toString();
  //     }
  //   }
  // }
  // onIncreaseProductCountValueClick(value: string) {
  //   if(!this.product.weightFlexible) {
  //     let intValue: number = Math.round(parseInt(value));
  //     if(intValue < 999) {
  //       this.productCountValue = (intValue + 1).toString();
  //     } else {
  //       this.productCountValue = (999).toString();
  //     }
  //   } else {
  //     let floatValue: number = parseFloat(parseFloat(value).toFixed(1));
  //     if(floatValue > 998.5) {
  //       this.productCountValue = (999).toString();
  //     } else {
  //       this.productCountValue = (floatValue + 0.5).toString();
  //     }
  //   }
  // }


}


