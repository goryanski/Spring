export interface  BasketProductInterface {
  productId: number,
  userId: number,
  name: string,
  photoPath: string,
  weight: number,
  weightMeasurement: string,
  weightFlexible: boolean,
  pricePerOneItem: number,
  generalCount: number,
  generalPrice: number,
  countInStorage: number
}
