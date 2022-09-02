import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../api/services/auth.service";
import {Location} from "@angular/common";
import {take, tap} from "rxjs";
import {UsersService} from "../../../api/services/users.service";
import {UpdatePersonRequestInterface} from "../../../api/interfaces/requests/update-person-request.interface";
import {BrowserLocalStorage} from "../../../shared/storage/local-storage";
import {AuthHelper} from "../../../shared/helpers/auth-helper";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  form: FormGroup;
  pattern = {
    full_name: '^[a-zA-Z ]{2,74}$', // English letters only, space (2-74 symbols)
    date_of_birth: '^[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}$', // Date of birth must be in format (dd/mm/yyyy) (full validation on the api side)
    username: '^[a-zA-Z_.^!0-9]{2,36}$', // English letters only, digits, symbols _.^! (2-36 symbols)
    password: '^[a-zA-Z_#@.^!0-9]{4,36}$', // English letters only, digits, symbols _#@.^! (4-36 symbols)
  }

  isError: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private readonly router: Router,
    private readonly usersService: UsersService,
    private location: Location,
    private readonly localStorage: BrowserLocalStorage,
    private readonly authHelper: AuthHelper
  ) {
    this.form = this.fb.group({
      'fullName': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.full_name)
        ]
      ),
      'email': this.fb.control(
        '',
        [
          Validators.email
        ]
      ),
      'dateOfBirth': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.date_of_birth)
        ]
      ),
      'username': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.username)
        ]
      ),
      'password': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.password)
        ]
      ),
    });
  }

  ngOnInit(): void {
  }


  onEditClick() {
    if (this.form.valid) {
      let person: UpdatePersonRequestInterface = this.form.value;
      if(!this.allFormFieldsAreEmpty(person)) {
        person.id = this.localStorage.getCurrentUserId();
        this.usersService.editPerson(person)
          .pipe(
            tap(
              response => {
                if(response.message == 'OK') {
                  if(person.username === '') {
                    this.isError = false;
                    // to change user-card data and show changes at once
                    // (pass event to user-card is not a good idea - user-card is
                    // in the other module)
                    document.location.reload();
                  } else {
                    // if username was changed successfully - log out user
                    // (because if username changes, user won't get access
                    // to any page - his jwt token based on username and won't be valid)
                    this.authHelper.logOut();
                  }
                }
                else {
                  this.errorMessage = response.message;
                  this.isError = true;
                }
              }),
            take(1)
          ).subscribe();
      }
    }
  }

  onBackClick() {
    this.location.back();
  }

  private allFormFieldsAreEmpty(person: UpdatePersonRequestInterface) {
    return person.fullName === ''
      && person.username === ''
      && person.email === ''
      && person.dateOfBirth === ''
      && person.password === '';
  }
}
