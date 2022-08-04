import { Component, OnInit } from '@angular/core';
import {HomeService} from "../../api/services/home.service";
import {Observable} from "rxjs";
import {CategoryInterface} from "../../api/interfaces/category.interface";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categoriesList$: Observable<CategoryInterface[]>;
  selectedCategoryId: number = 0;

  constructor(
    private readonly homeService: HomeService,
    private readonly router: Router,
  ) {
    this.categoriesList$ = homeService.getAllCategories();

    // TODO: check for connection and show error
  }

  ngOnInit(): void {
  }

  onCategoryClick(id: number) {
    this.selectedCategoryId = id;
    this.router.navigate([`/home/products-list/${id}`]);
  }
}
