import { Component, OnInit } from '@angular/core';
import {CategoryInterface} from "../../../api/interfaces/category.interface";
import {ShortProductInfoInterface} from "../../../api/interfaces/short-product-info.interface";
import {ViewportScroller} from "@angular/common";
import {ProductsByCategoryIdRequestInterface} from "../../../api/interfaces/requests/products-by-category-id-request.interface";
import {take} from "rxjs";
import {FavoritesProductsService} from "../../../api/services/favorites-products.service";
import {BrowserLocalStorage} from "../../../shared/storage/local-storage";
import {FavoriteProductsByCategoryIdRequestInterface} from "../../../api/interfaces/requests/favorite-products-by-category-id-request.interface";

@Component({
  selector: 'app-user-favorite-products',
  templateUrl: './user-favorite-products.component.html',
  styleUrls: ['./user-favorite-products.component.scss']
})
export class UserFavoriteProductsComponent implements OnInit {
  categoriesList: CategoryInterface[] = [];
  selectedCategoryId: number = 0;
  isCategoriesListEmpty: boolean = false;

  products: ShortProductInfoInterface[] = [];
  readonly pageSize: number = 2;
  currentPage: number = 1; // changes every time we click on pagination panel
  allProductsCount: number = 0;
  isProductsListEmpty: boolean = false;

  constructor(
    private readonly scroll: ViewportScroller,
    private readonly favoritesProductsService: FavoritesProductsService,
    private readonly localStorage: BrowserLocalStorage
  ) {
    favoritesProductsService.getFavoritesProductsCategories(this.localStorage.getCurrentUserId())
      .pipe(take(1))
      .subscribe(categories => {
        this.categoriesList.push(...categories);
        this.isCategoriesListEmpty = this.categoriesList.length == 0;
      });
  }

  ngOnInit(): void {}

  onCategoryClick(id: number) {
    this.selectedCategoryId = id;
    this.currentPage = 1;
    this.getProducts();
  }

  getProducts() {
    let requestObj: FavoriteProductsByCategoryIdRequestInterface = this.getRequestObject();
    this.favoritesProductsService.getFavoriteProductsByCategoryId(requestObj)
      .pipe(take(1))
      .subscribe(response => {
        this.allProductsCount = response.totalItems;
        this.isProductsListEmpty = this.allProductsCount == 0;
        this.products = response.products;
      });
  }

  getNextProducts() {
    this.getProducts();
    this.scroll.scrollToPosition([0,0]);
  }

  getRequestObject(): FavoriteProductsByCategoryIdRequestInterface {
    // calculate skip for pagination
    let skip: number;
    if(this.currentPage == 1) {
      skip = 0;
    } else {
      skip = (this.currentPage - 1) * this.pageSize;
    }

    return {
      userId: this.localStorage.getCurrentUserId(),
      categoryId: this.selectedCategoryId,
      skip: skip,
      limit: this.pageSize
    };
  }

  onProductRemovingFromFavoritesEvent(productIdToRemove: number) {
    // when user clicks on favorite label (unlike this product) - in product.component
    // (child component) an event occurs and we need to remove this product from array
    // stored here, by id we receive from event
    this.products.forEach( (product, index) => {
      if(product.id == productIdToRemove) {
        this.products.splice(index,1);
      }
    });

    // after that check - if products list is empty: if we on first page -
    // show no products message (changing this.isProductsListEmpty variable),
    // otherwise - return to previous page (and load products from previous page)
    if(this.products.length == 0) {
      if(this.currentPage == 1) {
        this.isProductsListEmpty = true;
      } else {
        this.currentPage -= 1;
        this.getNextProducts();
      }
    }
  }
}
