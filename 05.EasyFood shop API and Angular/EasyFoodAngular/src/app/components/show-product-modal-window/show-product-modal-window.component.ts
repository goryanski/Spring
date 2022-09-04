import {Component, EventEmitter, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {FullProductInfoInterface} from "../../api/interfaces/full-product-info.interface";
import {Observable, take} from "rxjs";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {ShowProductWindowService} from "../../api/services/show-product-window.service";
import {FavoriteProductRequestInterface} from "../../api/interfaces/requests/favorite-product-request.interface";
import {FavoritesProductsService} from "../../api/services/favorites-products.service";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";

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
  readonly similarProductsCount: number = 4;
  products$?: Observable<ShortProductInfoInterface[]>;

  isProductFavorite: boolean = false;
  isUserAuthenticated: boolean = false;
  isUserAdmin: boolean = false;



  constructor(
    public modal: NgbActiveModal,
    private readonly productsService: ShowProductWindowService,
    private readonly localStorage: BrowserLocalStorage,
    private readonly favoritesProductsService: FavoritesProductsService
  ) { }

  ngOnInit(): void {
    this.isUserAuthenticated = this.localStorage.isUserAuthenticated();
    this.productsService.getFullInfoProductById(this.productId)
      .pipe(take(1))
      .subscribe(res => {
        this.product = res;

        if(this.isUserAuthenticated) {
          // check if product is in favorites and set the appropriate label
          let requestObject: FavoriteProductRequestInterface = this.getFavoriteProductRequestObject();
          this.favoritesProductsService.isProductFavorite(requestObject)
            .pipe(take(1))
            .subscribe(response => {
              this.isProductFavorite = response;
              // getSimilarProducts (it's necessary do after setting this.isProductFavorite variable)
              this.products$ = this.productsService.getSimilarProducts(this.product.categoryId, this.similarProductsCount);
            });
        } else {
          // getSimilarProducts (we don't care about this.isProductFavorite variable if user is not authenticated)
          this.products$ = this.productsService.getSimilarProducts(this.product.categoryId, this.similarProductsCount);
        }


        // show button "Edit product" for admin
        if(this.isUserAuthenticated) {
          this.isUserAdmin = this.localStorage.getUserRole() === 'ROLE_ADMIN';
        }
      });
  }

  onFavoriteLabelClick() {
    let requestObject: FavoriteProductRequestInterface = this.getFavoriteProductRequestObject();
    if(!this.isProductFavorite) {
      this.favoritesProductsService.addProductToFavorites(requestObject)
        .pipe(take(1))
        .subscribe(response => {
          if(response.message === "OK") {
            this.isProductFavorite = true;
            this.product.likesCount++;
          }
        });
    } else {
      this.favoritesProductsService.deleteProductFromFavorites(requestObject)
        .pipe(take(1))
        .subscribe(response => {
          if(response.message === "OK") {
            this.isProductFavorite = false;
            this.product.likesCount--;
          }
        });
    }
  }

  private getFavoriteProductRequestObject(): FavoriteProductRequestInterface {
    return {
      userId: this.localStorage.getCurrentUserId(),
      productId: this.product.id
    }
  }

  onBtnEditProductClick() {
    this.modal.close()
  }

}
