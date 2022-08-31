import {BasketProductInterface} from "../basket-product.interface";

export interface BasketResponseInterface {
  products: BasketProductInterface[],
  basketPrice: number
}
