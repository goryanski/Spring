import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AdministrateProductsService} from "../../api/services/administrate-products.service";
import {take} from "rxjs";

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly administrateProductsService: AdministrateProductsService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      let productId: number = params['productId']; // productId - productId of param from app-routing.module
        this.administrateProductsService.getProductToEdit(productId)
          .pipe(take(1))
          .subscribe(product => {
              console.log(product);
          });
    });
  }

}
