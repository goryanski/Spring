import {Component, Input, OnInit} from '@angular/core';
import {ShortProductInfoInterface} from "../../api/interfaces/short-product-info.interface";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  @Input() product: ShortProductInfoInterface = {
    id: 0,
    name: '',
    price: 0,
    weight: 0,
    weightMeasurement: '',
    discount: 0,
    photoPath: ''
  };
  constructor() { }

  ngOnInit(): void {
  }
}
