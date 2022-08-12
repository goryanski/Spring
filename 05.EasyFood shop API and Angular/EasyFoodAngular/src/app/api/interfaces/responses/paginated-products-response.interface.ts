import {ShortProductInfoInterface} from "../short-product-info.interface";

export interface PaginatedProductsResponseInterface {
  totalItems: number,
  totalPages: number,
  currentPage: number,
  products: ShortProductInfoInterface[];
}
