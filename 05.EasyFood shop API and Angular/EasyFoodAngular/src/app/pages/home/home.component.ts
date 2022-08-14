import {Component, OnInit} from '@angular/core';
import {HomePageService} from "../../api/services/home-page.service";
import {Observable, take} from "rxjs";
import {CategoryInterface} from "../../api/interfaces/category.interface";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {ProductsByCategoryIdRequestInterface} from "../../api/interfaces/requests/products-by-category-id-request.interface";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categoriesList: CategoryInterface[] = [];
  selectedCategoryId: number = 0;
  isConnectionError = false;

  products: ShortProductInfoInterface[] = [];
  readonly pageSize: number = 4;
  currentPage: number = 1; // changes every time we click on pagination panel
  allProductsCount: number = 0;
  isProductsListEmpty: boolean = false;

  constructor(
    private readonly homeService: HomePageService,
  ) {
    homeService.getAllCategories()
      .pipe(take(1))
      .subscribe(categories => this.categoriesList.push(...categories));

    // check if connection error
    setTimeout(() => {
      if(this.categoriesList.length == 0) {
        this.isConnectionError = true;
      }
    },3000);
  }

  ngOnInit(): void {}

  onCategoryClick(id: number) {
    this.selectedCategoryId = id;
    this.currentPage = 1;
    this.getProducts();
  }


  getNextProducts() {
    this.getProducts();
  }

  getProducts() {
    let requestObj: ProductsByCategoryIdRequestInterface = this.getRequestObject();
    this.homeService.getProductsByCategoryId(requestObj)
      .pipe(take(1))
      .subscribe(response => {
        this.allProductsCount = response.totalItems;
        if(this.allProductsCount == 0) {
          this.isProductsListEmpty = true;
        }
        this.products = response.products;
      });
  }

  getRequestObject(): ProductsByCategoryIdRequestInterface {
    return {
      categoryId: this.selectedCategoryId,
      currentPage: this.currentPage - 1,
      pageSize: this.pageSize
    };
  }
}
