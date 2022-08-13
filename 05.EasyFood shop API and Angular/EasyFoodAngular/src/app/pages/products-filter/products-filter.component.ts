import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {CountryInterface} from "../../api/interfaces/country.interface";

@Component({
  selector: 'app-products-filter',
  templateUrl: './products-filter.component.html',
  styleUrls: ['./products-filter.component.scss']
})
export class ProductsFilterComponent implements OnInit {
  counties$?: Observable<CountryInterface[]>;
  //brands$?: Observable<any[]>;
  testCountries: CountryInterface[] = [
    {id: 1, name: "Ukraine"},
    {id: 2, name: "Poland"},
    {id: 3, name: "USA"}
  ]
  selectedCountryName: string = 'Choose a country';
  countryIsNotSelected: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  onDropdownCountryClick(country: CountryInterface) {
    this.countryIsNotSelected = false;
    this.selectedCountryName = country.name;
    // use id of the selected country
    console.log(country.id);
  }

  onDropdownCountryIsNotSelectedClick() {
    this.countryIsNotSelected = true;
    this.selectedCountryName = 'Not selected';
  }
}
