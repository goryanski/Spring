import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AdministrateProductsService} from "../../api/services/administrate-products.service";
import {take} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Location} from "@angular/common";
import {LoginPersonRequestInterface} from "../../api/interfaces/requests/login-person-request.interface";
import {tap} from "rxjs/operators";
import {EditProductInterface} from "../../api/interfaces/edit-product.interface";
import {ProductLinkedDataResponseInterface} from "../../api/interfaces/responses/product-linked-data-response.interface";




@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {
  form: FormGroup;
  pattern = {
    countryName: '^[A-Z]{1}[a-z]{2,30}$', // English letters only, first letter is capitalized, other - lowercase (3-30 characters)
    measurementName: '^[A-Za-z]{1,4}$', // English letters only, (1-4 characters)
    categoryName: '^[A-Z]{1}[a-z ]{2,40}$', // English letters only, space, first letter is capitalized, other - lowercase (3-40 characters)
    brandName: '^[a-zA-Z 0-9.%#@&$]{3,30}$' // English letters only, digits, space, symbols .%#@&$ (3-30 characters)
  }

  oldProductData?: EditProductInterface;
  productLinkedData?: ProductLinkedDataResponseInterface;
  productId: number = 0;

  // dropdowns
  selectedCountryName: string = '';
  selectedMeasurementName: string = '';
  selectedCategoryName: string = '';
  selectedBrandName: string = '';

  // inputs (type number)
  priceInputError: boolean = false;
  weightInputError: boolean = false;
  discountInputError: boolean = false;
  amountInStorageInputError: boolean = false;

  // error handle
  isEditError: boolean = false;
  apiResponseErrorMessage: string = '';


  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly administrateProductsService: AdministrateProductsService,
    private fb: FormBuilder,
    private readonly router: Router,
    private location: Location
  ) {
    this.form = this.fb.group({
      'name': this.fb.control(
        '',
        [
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ),
      'description': this.fb.control(
        '',
        [
          Validators.minLength(3),
          Validators.maxLength(500)
        ]
      ),
      'price': this.fb.control(
        '',
        [
          Validators.nullValidator,
          Validators.min(0.01)
        ]
      ),
      'weight': this.fb.control(
        '',
        []
      ),
      'discount': this.fb.control(
        '',
        []
      ),
      'photoPath': this.fb.control(
        '',
        [
          Validators.minLength(4),
          Validators.maxLength(600)
        ]
      ),
      'amountInStorage': this.fb.control(
        '',
        []
      ),
      'weightFlexible': this.fb.control(
        '',
        []
      ),
      'available': this.fb.control(
        '',
        []
      ),
      'categoryName': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.categoryName)
        ]
      ),
      'brandName': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.brandName)
        ]
      ),
      'countryName': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.countryName)
        ]
      ),
      'measurementName': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.measurementName)
        ]
      ),
    });
  }


  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      let productId: number = params['productId']; // productId - productId of param from app-routing.module
      this.productId = productId;
        this.administrateProductsService.getProductToEdit(productId)
          .pipe(take(1))
          .subscribe(product => {
            if(product != null) {
              // set to form the product info we received
              this.form.patchValue({
                name: product.name,
                description: product.description,
                price: product.price,
                weight: product.weight,
                discount: product.discount,
                amountInStorage: product.amountInStorage,
                photoPath: product.photoPath,
                weightFlexible: product.weightFlexible,
                available: product.available
              }, {emitEvent: false});

              // remember all info we received
              this.oldProductData = product;

              this.loadProductLinkedData(product)
            }
          });
    });
  }

  private loadProductLinkedData(product: EditProductInterface) {
    this.administrateProductsService.getProductLinkedData()
      .pipe(take(1))
      .subscribe(data => {
        this.productLinkedData = data;

        // set init data to dropdowns
        this.selectedCountryName = product.countryName;
        this.selectedMeasurementName = product.measurementName;
        this.selectedCategoryName = product.categoryName;
        this.selectedBrandName = product.brandName;
      });
  }


  // dropdowns click handle
  onDropdownCountryClick(countryName: string) {
    this.selectedCountryName = countryName;
  }
  onDropdownMeasurementsClick(measurementName: string) {
    this.selectedMeasurementName = measurementName;
  }
  onDropdownCategoryClick(categoryName: string) {
    this.selectedCategoryName = categoryName;
  }
  onDropdownBrandsClick(brandName: string) {
    this.selectedBrandName = brandName;
  }


  onEditClick() {
    if (this.form.valid) {
      let product: EditProductInterface = this.form.value;
      if(this.numericFieldsValid(product)) {
        this.enrichProduct(product);
        this.administrateProductsService.editProduct(product)
          .pipe(
          tap(
            response => {
              if(response.message === 'OK') {
                console.log('OK');
                // go to found-products page to show ...
                // this.router.navigate([`/found-products/${requestObject.name}`]);
                // or try  this.location.back()  - if data updates
              }
              else {
                this.apiResponseErrorMessage = response.message;
                this.isEditError = true;
              }
            }),
          take(1)
        ).subscribe();
      }
    }
  }

  numericFieldsValid(product: EditProductInterface): boolean {
    // input type"number" allows write the letter "e", but if user writes it - value will be null
    this.priceInputError = product.price == null;
    this.weightInputError = product.weight == null;
    // not null and whole number ('10.0' % 1 -> returns 0; 10 % 1 -> returns 0; '10.5' % 1 -> returns 0.5)
    this.discountInputError = product.discount == null || product.discount % 1 != 0;

    this.amountInStorageInputError = product.amountInStorage == null;

    return !this.priceInputError && !this.weightInputError
      && !this.discountInputError && !this.amountInStorageInputError;
  }

  onBackClick() {
    this.location.back();
  }


  private enrichProduct(product: EditProductInterface) {
    product.id =  this.productId;
    product.countryName = this.selectedCountryName;
    product.measurementName = this.selectedMeasurementName;
    product.categoryName = this.selectedCategoryName;
    product.brandName = this.selectedBrandName;
  }
}
