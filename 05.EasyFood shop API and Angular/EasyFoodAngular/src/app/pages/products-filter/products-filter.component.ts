import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Observable, take} from "rxjs";
import {CountryInterface} from "../../api/interfaces/country.interface";
import {ProductsFilterService} from "../../api/services/products-filter.service";
import {BrandInterface} from "../../api/interfaces/brand.interface";
import {FilterProductsRequestInterface} from "../../api/interfaces/requests/filter-products-request.interface";
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";
import {ProductsByCategoryIdRequestInterface} from "../../api/interfaces/requests/products-by-category-id-request.interface";
import {ViewportScroller} from "@angular/common";

@Component({
  selector: 'app-products-filter',
  templateUrl: './products-filter.component.html',
  styleUrls: ['./products-filter.component.scss']
})
export class ProductsFilterComponent implements OnInit {
  counties$: Observable<CountryInterface[]>;
  selectedCountryName: string = 'Choose a country';
  selectedCountryId: number = 0;

  brands$: Observable<BrandInterface[]>;
  selectedBrandName: string = 'Choose a brand';
  selectedBrandId: number = 0;

  @ViewChild('priceRangeControl') priceRangeControl: ElementRef | undefined;
  currentPriceValue: number = 600;

  isDiscountCheckboxActive: boolean = false;
  isMostPopularFirstCheckboxActive: boolean = false;

  readonly pageSize: number = 5;
  currentPage: number = 1;
  products: ShortProductInfoInterface[] = [];
  allProductsCount: number = 0;
  isProductsListEmpty: boolean = false;
  isBtnFilterWasClicked: boolean = false;

  constructor(
    private readonly productsFilterService: ProductsFilterService,
    private readonly scroll: ViewportScroller
  ) {
    this.counties$ = this.productsFilterService.getAllCountries();
    this.brands$ = this.productsFilterService.getAllBrands();
  }

  ngOnInit(): void {}


  onDropdownCountryClick(country: CountryInterface) {
    this.selectedCountryName = country.name;
    this.selectedCountryId = country.id;
  }
  onDropdownCountryIsNotSelectedClick() {
    this.selectedCountryName = 'Not selected';
    this.selectedCountryId = 0;
  }

  onDropdownBrandClick(brand: BrandInterface) {
    this.selectedBrandName = brand.name;
    this.selectedBrandId = brand.id;
  }
  onDropdownBrandIsNotSelectedClick() {
    this.selectedBrandName = 'Not selected';
    this.selectedBrandId = 0;
  }

  onChangePriceRange() {
    if(this.priceRangeControl != undefined) {
      this.currentPriceValue = this.priceRangeControl.nativeElement.value;
    }
  }

  onBtnFilterClick() {
    this.currentPage = 1;
    this.isBtnFilterWasClicked = true;
    this.getProducts();
  }

  getNextProducts() {
    this.getProducts();
    this.scroll.scrollToPosition([0,0]);
  }

  getProducts() {
    let requestObject: FilterProductsRequestInterface = this.getRequestObject();
    this.productsFilterService.getFilteredProducts(requestObject)
      .pipe(take(1))
      .subscribe(response => {
        this.allProductsCount = response.totalItems;
        this.isProductsListEmpty = this.allProductsCount == 0;
        this.products = response.products;
      });
  }

  getRequestObject(): FilterProductsRequestInterface {
    // calculate skip for pagination
    let skip: number;
    if(this.currentPage == 1) {
      skip = 0;
    } else {
      skip = (this.currentPage - 1) * this.pageSize;
    }

    // define requestObject
    return {
      countryId: this.selectedCountryId,
      brandId: this.selectedBrandId,
      maxPrice: this.currentPriceValue,
      withDiscount: this.isDiscountCheckboxActive,
      popularFirst: this.isMostPopularFirstCheckboxActive,
      skip: skip,
      limit: this.pageSize
    }
  }
}
