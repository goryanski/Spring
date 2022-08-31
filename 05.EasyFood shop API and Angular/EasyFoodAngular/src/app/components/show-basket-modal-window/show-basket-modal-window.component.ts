import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {BasketService} from "../../api/services/basket.service";
import {take} from "rxjs";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {BasketProductInterface} from "../../api/interfaces/basket-product.interface";
import {RemoveBasketProductRequestInterface} from "../../api/interfaces/requests/remove-basket-product-request.interface";
import {AuthHelper} from "../../shared/helpers/auth-helper";
import {MakeOrderRequestInterface} from "../../api/interfaces/requests/make-order-request.interface";
import {OrdersService} from "../../api/services/orders.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-show-basket-modal-window',
  templateUrl: './show-basket-modal-window.component.html',
  styleUrls: ['./show-basket-modal-window.component.scss']
})
export class ShowBasketModalWindowComponent implements OnInit {
  products: BasketProductInterface[] = [];
  basketPrice: number = 0;
  isProductsListEmpty: boolean = false;
  showSuccessOrderMessage: boolean = false;

  form: FormGroup;
  pattern = {
    phone: '^[0]{1}[1-9]{1}[0-9]{8}$', // example: 0934094350
  }

  constructor(
    public modal: NgbActiveModal,
    private readonly basketService: BasketService,
    private readonly localStorage: BrowserLocalStorage,
    private readonly authHelper: AuthHelper,
    private readonly ordersService: OrdersService,
    private fb: FormBuilder,
  ) {
    this.form = this.fb.group({
      'phoneNumber': this.fb.control(
        '',
        [
          Validators.required,
          Validators.pattern(this.pattern.phone)
        ]
      )
    });
  }

  ngOnInit(): void {
    this.basketService.getAllBasketProducts(this.localStorage.getCurrentUserId())
      .pipe(take(1))
      .subscribe(response => {
        this.products = response.products;
        this.isProductsListEmpty = response.products.length == 0;
        this.basketPrice = response.basketPrice;
      });
  }

  onRemoveProductEvent(requestObject: RemoveBasketProductRequestInterface) {
      this.products.forEach( (product, index) => {
        if(product.productId == requestObject.productId
            && product.userId == requestObject.userId) {
          this.products.splice(index,1);
        }
      });
    }

  onProductPriceChangedEvent() {
    let basketPrice: number = 0;
    this.products.forEach( (product) => {
      basketPrice += product.generalPrice;
    });
    this.basketPrice = parseFloat(basketPrice.toFixed(2));

    // Where we go through all products and calculate basket price.  We don’t need to send any params in this event, because in the child component we changed field product.generalPrice and here product in this array was changed as well – we’ve passed it by link
  }

  onClearBasketClick() {
    this.clearBasket();
  }

  onConfirmOrderClick(phoneNumber: string) {
    if(this.form.valid) {
      let requestObject: MakeOrderRequestInterface = this.getRequestObject(phoneNumber);
      this.ordersService.makeOrder(requestObject)
        .pipe(take(1))
        .subscribe(response => {
           if(response.message === "OK") {
             this.clearBasket();
             this.showSuccessOrderMessage = true;
           } else {
             console.log(response.message);
           }
        });
    }
  }

  private clearBasket() {
    // remove all in DB
    this.basketService.deleteAllProducts(this.localStorage.getCurrentUserId())
      .pipe(take(1))
      .subscribe();

    // remove all in this array
    this.products.splice(0, this.products.length);

    this.isProductsListEmpty = true;
    this.basketPrice = 0.0;
    this.authHelper.clearCountProductsInBasket();
  }

  private getRequestObject(phoneNumber: string): MakeOrderRequestInterface {
    return {
      userId: this.localStorage.getCurrentUserId(),
      basketPrice: this.basketPrice,
      phoneNumber: phoneNumber
    }
  }
}
