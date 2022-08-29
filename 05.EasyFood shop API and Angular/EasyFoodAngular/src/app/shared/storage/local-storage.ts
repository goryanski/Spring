import {Injectable} from "@angular/core";
import {AbstractStorage} from "./storage.abstract";

@Injectable()
export class BrowserLocalStorage implements AbstractStorage {
  private readonly storage: Storage;

  constructor() {
    this.storage = window.localStorage;
  }

  get length(): number {
    return this.storage.length;
  }

  clear(): void {
    this.storage.clear();
  }

  getItem(key: string): string | null {
    return this.storage.getItem(key);
  }

  key(index: number): string | null {
    return this.storage.key(index);
  }

  removeItem(key: string): void {
    this.storage.removeItem(key);
  }

  setItem(key: string, value: string): void {
    this.storage.setItem(key, value);
  }

  isUserAuthenticated(): boolean {
    let token = this.getItem('accessToken');
    return !(token == null || token == 'none');
  }
  getUserRole(): string {
    let role = this.getItem('currentUserRole');
    return role != null ? role : 'none';
  }
  getCurrentUserId(): number {
    let id = localStorage.getItem('currentUserId');
    if(id != null && id != 'none') {
      return parseInt(id);
    }
    return 0;
  }

  getCountProductsInBasket() {
    let countProducts = localStorage.getItem('countProductsInBasket');
    return countProducts == null ? null : parseInt(countProducts);
  }

  increaseBasketProductsCount() {
    let countProducts = localStorage.getItem('countProductsInBasket');
    if(countProducts != null) {
      localStorage.setItem('countProductsInBasket', (parseInt(countProducts) + 1).toString())
    }
  }

  reduceBasketProductsCount() {
    let countProducts = localStorage.getItem('countProductsInBasket');
    if(countProducts != null) {
      localStorage.setItem('countProductsInBasket', (parseInt(countProducts) - 1).toString())
    }
  }
}
