import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {Location} from '@angular/common';
import {LoginPersonRequestInterface} from "../../api/interfaces/requests/login-person-request.interface";
import {AuthService} from "../../api/services/auth.service";
import {LoginService} from "./login.service";
import {tap} from "rxjs/operators";
import {take} from "rxjs";
import {AuthHelper} from "../../shared/helpers/auth-helper";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  pattern = {
    username: '^[a-zA-Z_.^!0-9]{2,36}$', // English letters only, digits, symbols _.^! (2-36 symbols)
    password: '^[a-zA-Z_#@.^!0-9]{4,36}$', // English letters only, digits, symbols _#@.^! (4-36 symbols)
  }

  isLoginError: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private readonly router: Router,
    private location: Location,
    private readonly loginService: LoginService,
    private readonly authHelper: AuthHelper
  ) {
    this.form = this.fb.group({
      'username': this.fb.control(
        'igoryan', // barbara
        [
          Validators.required,
          Validators.pattern(this.pattern.username)
        ]
      ),
      'password': this.fb.control(
        '123456Homo01odWq12444',// 2222
        [
          Validators.required,
          Validators.pattern(this.pattern.password)
        ]
      )
    })
  }

  ngOnInit(): void {}

  onLoginClick() {
    if (this.form.valid) {
      let requestObject: LoginPersonRequestInterface = this.form.value;
      this.loginService.login(requestObject).pipe(
        tap(
          exception => {
            if(exception == 'none') {
              // change links logOut, login, etc. (in a header)
              this.authHelper.setAuthenticatedUserState();
              this.router.navigate(['/']);
            }
            else {
              this.errorMessage = exception;
              this.isLoginError = true;
            }
          }),
        take(1)
      ).subscribe();
    }
  }

  onBackClick() {
    this.location.back();
  }
}
