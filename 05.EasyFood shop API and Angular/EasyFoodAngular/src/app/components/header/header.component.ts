import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  // searchField - id of input, starts with #
  @ViewChild('searchField') searchField: ElementRef | undefined;

  form: FormGroup;
  pattern = {
    search: '^[a-zA-Z]{2,18}$', // English letters only, 2-18 characters length
  }

  constructor(
    private fb: FormBuilder,
    private readonly router: Router
  ) {
    this.form = this.fb.group({
      'search': this.fb.control(
        '',
        [
          Validators.pattern(this.pattern.search)
        ]
      )
    });
  }

  ngOnInit(): void {}

  btnSearchProductsClick() {
    if(this.searchField != undefined) {
      let text: string = this.searchField.nativeElement.value;
      if(text != '' && this.form.valid) {
        this.router.navigate([`found-products/${text}`]);
      }
    }
  }

  logOut() {

  }
}
