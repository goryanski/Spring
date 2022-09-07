import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ProductLinkedDataResponseInterface} from "../../api/interfaces/responses/product-linked-data-response.interface";
import {ActivatedRoute, Router} from "@angular/router";
import {AdministrateProductsService} from "../../api/services/administrate-products.service";
import {Location} from "@angular/common";
import {take} from "rxjs";
import {EditProductInterface} from "../../api/interfaces/edit-product.interface";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-edit-products',
  templateUrl: './edit-products.component.html',
  styleUrls: ['./edit-products.component.scss']
})
export class EditProductsComponent implements OnInit {
  form: FormGroup;
  pattern = {
    countryName: '^[A-Z]{1}[a-z]{2,29}$', // English letters only, first letter is capitalized, other - lowercase (3-30 characters)
    measurementName: '^[A-Za-z]{1,4}$', // English letters only, (1-4 characters)
    categoryName: '^[A-Z]{1}[a-z ]{2,39}$', // English letters only, space, first letter is capitalized, other - lowercase (3-40 characters)
    brandName: '^[a-zA-Z 0-9.%#@&$]{3,30}$' // English letters only, digits, space, symbols .%#@&$ (3-30 characters)
  }

  productLinkedData?: ProductLinkedDataResponseInterface;
  @Input() productId: number = 0;
  isEditProductMode: boolean = false;

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

  buttonLabel: string = '';


  constructor(
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
          Validators.maxLength(100),
          Validators.required
        ]
      ),
      'description': this.fb.control(
        '',
        [
          Validators.minLength(3),
          Validators.maxLength(500),
          Validators.required
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
          Validators.maxLength(600),
          Validators.required
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
    // in this component we are editing a product or adding a product - depends on whether the @Input() parameter (this.productId) was passed to this component or not
    this.isEditProductMode = this.productId != 0;
    if(this.isEditProductMode) {
      this.administrateProductsService.getProductToEdit(this.productId)
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

            this.loadProductLinkedData(product);
          }
        });
    } else {
      this.loadProductLinkedData(null);
    }
    this.buttonLabel = this.isEditProductMode ? 'Edit' : 'Add';
  }

  private loadProductLinkedData(product: EditProductInterface | null) {
    this.administrateProductsService.getProductLinkedData()
      .pipe(take(1))
      .subscribe(data => {
        this.productLinkedData = data;

        // set init data to dropdowns
        if(product != null) {
          // if edit mode - set data of product we receive from api
          this.selectedCountryName = product.countryName;
          this.selectedMeasurementName = product.measurementName;
          this.selectedCategoryName = product.categoryName;
          this.selectedBrandName = product.brandName;
        } else {
          // otherwise - set the first element of array
          this.selectedCountryName = data.countries[0];
          this.selectedMeasurementName = data.weightMeasurements[0];
          this.selectedCategoryName = data.categories[0];
          this.selectedBrandName = data.brands[0];
        }
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
        if(this.isEditProductMode) {
          this.administrateProductsService.editProduct(product)
            .pipe(
              tap(
                response => {
                  if(response.message === 'OK') {
                    // go to found-products page to show updated product
                    this.router.navigate([`/found-products/${product.name}`]);
                  }
                  else {
                    this.apiResponseErrorMessage = response.message;
                    this.isEditError = true;
                  }
                }),
              take(1)
            ).subscribe();
        } else {
          // add product
          this.administrateProductsService.addProduct(product)
            .pipe(
              tap(
                response => {
                  if(response.message === 'OK') {
                    alert('product added successfully');
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
  }

  numericFieldsValid(product: EditProductInterface): boolean {
    // input type"number" allows write the letter "e", but if user writes it - value will be null
    // if user just leaves input empty - input will have type string. type checking - cannot allows empty fields
    this.priceInputError = product.price == null || typeof product.price == 'string';
    this.weightInputError = product.weight == null || typeof product.weight == 'string';
    // not null and whole number ('10.0' % 1 -> returns 0; 10 % 1 -> returns 0; '10.5' % 1 -> returns 0.5)
    this.discountInputError = product.discount == null || product.discount % 1 != 0 || typeof product.discount == 'string';

    this.amountInStorageInputError = product.amountInStorage == null || typeof product.amountInStorage == 'string';

    return !this.priceInputError && !this.weightInputError
      && !this.discountInputError && !this.amountInStorageInputError;
  }

  onBackClick() {
    this.location.back();
  }


  private enrichProduct(product: EditProductInterface) {
    // if we add product set id = 0
    product.id = this.isEditProductMode ? this.productId : 0;

    if(this.form.controls['categoryName'].value === '') {
      // if user doesn't write a new category name in newCategoryNameInput - set categoryName from dropdown
      product.categoryName = this.selectedCategoryName;
    } else {
      // otherwise - set categoryName from that input (it'll create a new category in DB)
      product.categoryName = this.form.controls['categoryName'].value;
    }

    //  do the same with other similar fields
    if(this.form.controls['countryName'].value === '') {
      product.countryName = this.selectedCountryName;
    } else {
      product.countryName = this.form.controls['countryName'].value;
    }
    if(this.form.controls['brandName'].value === '') {
      product.brandName = this.selectedBrandName;
    } else {
      product.brandName = this.form.controls['brandName'].value;
    }
    if(this.form.controls['measurementName'].value === '') {
      product.measurementName = this.selectedMeasurementName;
    } else {
      product.measurementName = this.form.controls['measurementName'].value;
    }

    // if it's product adding and user didn't check th checkbox - it will be type string and it'll cause the error on api side
    if(typeof this.form.controls['available'].value == 'string') {
      product.available = false;
    }
    if(typeof this.form.controls['weightFlexible'].value == 'string') {
      product.weightFlexible = false;
    }
  }
}
