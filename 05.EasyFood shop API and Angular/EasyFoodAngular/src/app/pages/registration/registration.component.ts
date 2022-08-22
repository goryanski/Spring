import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../api/services/auth.service";
import {RegisterPersonRequestInterface} from "../../api/interfaces/requests/register-person-request.interface";
import {take, tap} from "rxjs";
import {Location} from '@angular/common';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  form: FormGroup;
  pattern = {
    full_name: '^[a-zA-Z ]{2,74}$', // English letters only, space (2-74 symbols)
    date_of_birth: '^[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}$', // Date of birth must be in format (dd/mm/yyyy) (full validation on the api side)
    username: '^[a-zA-Z_.^!0-9]{2,36}$', // English letters only, digits, symbols _.^! (2-36 symbols)
    password: '^[a-zA-Z_#@.^!0-9]{4,36}$', // English letters only, digits, symbols _#@.^! (4-36 symbols)
  }

  isRegistrationError: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private readonly router: Router,
    private readonly authService: AuthService,
    private location: Location
  ) {
    this.form = this.fb.group({
      'fullName': this.fb.control(
        'Ivanov Igor Igorovich',
        [
          Validators.required,
          Validators.pattern(this.pattern.full_name)
        ]
      ),
      'email': this.fb.control(
        'igorok2083@gmail.com',
        [
          Validators.required,
          Validators.email
        ]
      ),
      'dateOfBirth': this.fb.control(
        '11/01/1995',
        [
          Validators.required,
          Validators.pattern(this.pattern.date_of_birth)
        ]
      ),
      'username': this.fb.control(
        'igory',
        [
          Validators.required,
          Validators.pattern(this.pattern.username)
        ]
      ),
      'password': this.fb.control(
        '123456Homo01odWq12444',
        [
          Validators.required,
          Validators.pattern(this.pattern.password)
        ]
      ),
    });
  }

  ngOnInit(): void {
  }

  onRegisterClick() {
    if (this.form.valid) {
      let person: RegisterPersonRequestInterface = this.form.value;
      //console.log(person);
      this.authService.registerPerson(person)
        .pipe(
          tap(
            registration => {
              if(registration.message == 'OK') {
                this.isRegistrationError = false;
                this.router.navigate(['/']);
              }
              else {
                this.errorMessage = registration.message;
                this.isRegistrationError = true;
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
