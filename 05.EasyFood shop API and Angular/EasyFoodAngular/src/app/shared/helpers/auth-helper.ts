import {Injectable} from "@angular/core";
import {BrowserLocalStorage} from "../storage/local-storage";
import {Router} from "@angular/router";
import {BasketService} from "../../api/services/basket-service";
import {take} from "rxjs";

@Injectable()
export class AuthHelper {
  profileLink: any;
  basketLink: any;
  adminPanelLink: any;
  loginLink: any;
  registrationLink: any;
  logOutLink: any;

  countProductsInBasketBadge: any;

  constructor(
    private readonly localStorage: BrowserLocalStorage,
    private readonly router: Router,
    private readonly ordersService: BasketService
  ) {}

  initFields() {
    this.profileLink = document.getElementById('profileLink');
    this.basketLink = document.getElementById('basketLink');
    this.adminPanelLink = document.getElementById('adminPanelLink');
    this.loginLink = document.getElementById('loginLink');
    this.registrationLink = document.getElementById('registrationLink');
    this.logOutLink = document.getElementById('logOutLink');
    this.countProductsInBasketBadge = document.getElementById('countProductsInBasketBadge');
  }

  checkAndSetAuthUserState() {
    if(this.localStorage.isUserAuthenticated()) {
      this.setAuthenticatedUserState();
    }
    else {
      this.setNonAuthenticatedUserState();
    }
  }

  setAuthenticatedUserState() {
    this.initFields();

    if(this.registrationLink != null) {
      this.registrationLink.style.display = "none";
    }
    if(this.logOutLink != null) {
      this.logOutLink.style.display = "block";
    }
    if(this.loginLink != null) {
      this.loginLink.style.display = "none";
    }

    let role = this.localStorage.getUserRole();
    if(role != 'none') {
      switch (role) {
        case 'ROLE_USER': {
          if(this.profileLink != null) {
            this.profileLink.style.display = "block";
          }
          if(this.basketLink != null) {
            this.basketLink.style.display = "block";
          }
          break;
        }
        case 'ROLE_ADMIN': {
          if(this.adminPanelLink != null) {
            this.adminPanelLink.style.display = "block";
          }
          break;
        }
      }
    }

    this.setCountProductsInBasket();
  }

  setNonAuthenticatedUserState() {
    this.initFields();
    if(this.logOutLink != null) {
      this.logOutLink.style.display = "none";
    }
    if(this.registrationLink != null) {
      this.registrationLink.style.display = "block";
    }
    if(this.loginLink != null) {
      this.loginLink.style.display = "block";
    }
    if(this.profileLink != null) {
      this.profileLink.style.display = "none";
    }
    if(this.adminPanelLink != null) {
      this.adminPanelLink.style.display = "none";
    }
    if(this.basketLink != null) {
      this.basketLink.style.display = "none";
    }
  }

  public logOut() {
    this.setNonAuthenticatedUserState();
    this.clearLocalStorage();
    this.router.navigate(['/']);
  }

  clearLocalStorage() {
    this.localStorage.removeItem('accessToken');
    this.localStorage.removeItem('currentUserRole');
    this.localStorage.removeItem('currentUserId');
    this.localStorage.removeItem('countProductsInBasket');
    // to destroy the current component (because, for example, currentUserId can be saved in a component and after user logout, user still can do something)
    setTimeout(() => {
      document.location.reload();
    },500);
  }

  private setCountProductsInBasket() {
    this.ordersService.getUserProductsCountInBasket(this.localStorage.getCurrentUserId())
      .pipe(take(1))
      .subscribe(response => {
        localStorage.setItem('countProductsInBasket', response.toString());
        this.setCountProductsInBasketToBadge();
      });
  }

  public increaseBasketProductsCount() {
    this.localStorage.increaseBasketProductsCount();
    this.setCountProductsInBasketToBadge();
  }

  public reduceBasketProductsCount() {
    this.localStorage.reduceBasketProductsCount();
    this.setCountProductsInBasketToBadge();
  }

  private setCountProductsInBasketToBadge() {
    let countProducts = this.localStorage.getCountProductsInBasket();
    if(countProducts != null) {
      this.countProductsInBasketBadge.innerText = countProducts;
    }
  }
}
