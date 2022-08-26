import {Injectable} from "@angular/core";
import {BrowserLocalStorage} from "../storage/local-storage";

@Injectable()
export class AuthHelper {
  profileLink: any;
  basketLink: any;
  adminPanelLink: any;
  loginLink: any;
  registrationLink: any;
  logOutLink: any;

  constructor(private readonly localStorage: BrowserLocalStorage) {}

  initFields() {
    this.profileLink = document.getElementById('profileLink');
    this.basketLink = document.getElementById('basketLink');
    this.adminPanelLink = document.getElementById('adminPanelLink');
    this.loginLink = document.getElementById('loginLink');
    this.registrationLink = document.getElementById('registrationLink');
    this.logOutLink = document.getElementById('logOutLink');
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

  clearLocalStorage() {
    this.localStorage.removeItem('accessToken');
    this.localStorage.removeItem('currentUserRole');
    this.localStorage.removeItem('currentUserId');
    // to destroy the current component (because, for example, currentUserId can be saved in a component and after user logout, user still can do something)
    setTimeout(() => {
      document.location.reload();
    },500);
  }
}
