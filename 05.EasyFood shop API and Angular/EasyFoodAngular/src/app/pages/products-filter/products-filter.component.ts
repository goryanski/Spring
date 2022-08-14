import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Observable} from "rxjs";
import {CountryInterface} from "../../api/interfaces/country.interface";
import {ProductsFilterService} from "../../api/services/products-filter.service";
import {BrandInterface} from "../../api/interfaces/brand.interface";

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

  @ViewChild('discountCheckbox') discountCheckbox: ElementRef | undefined;
  discount: boolean = false;
  isDiscountCheckboxActive: boolean = false;

  constructor(
    private readonly productsFilterService: ProductsFilterService
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


  onDiscountCheckboxChange() {
    if(this.discountCheckbox != undefined) {
      console.log(this.discountCheckbox.nativeElement);
    }
  }
}
