export interface FilterProductsRequestInterface {
  countryId: number,
  brandId: number,
  maxPrice: number,
  withDiscount: boolean,
  popularFirst: boolean,
  skip: number,
  limit: number
}
