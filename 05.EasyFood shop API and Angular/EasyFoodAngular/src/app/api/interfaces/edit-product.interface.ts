export interface EditProductInterface {
  id: number,
  name: string,
  description: string,
  price: number,
  weight: number,
  discount: number,
  isAvailable: boolean,
  photoPath: string,
  amountInStorage: number;
  likesCount: number,
  isWeightFlexible: boolean,
  categoryName: string,
  brandName: string,
  countryName: string,
  weightMeasurement: string
}
