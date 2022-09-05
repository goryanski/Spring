export interface EditProductInterface {
  id: number,
  name: string,
  description: string,
  price: number,
  weight: number,
  discount: number,
  available: boolean,
  photoPath: string,
  amountInStorage: number,
  weightFlexible: boolean,
  categoryName: string,
  brandName: string,
  countryName: string,
  measurementName: string
}
