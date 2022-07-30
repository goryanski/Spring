import { Component, OnInit } from '@angular/core';
import {HomeService} from "../../api/services/home.service";
import {Observable} from "rxjs";
import {CategoryInterface} from "../../api/interfaces/category.interface";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categoriesList$: Observable<CategoryInterface[]>;

  constructor(
    private readonly homeService: HomeService
  ) {
    this.categoriesList$ = homeService.getAllCategories();
  }

  ngOnInit(): void {
  }
}
