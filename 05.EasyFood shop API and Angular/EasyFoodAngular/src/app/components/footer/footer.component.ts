import { Component, OnInit } from '@angular/core';
import {ViewportScroller} from "@angular/common";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  constructor(private readonly scroll: ViewportScroller) {}

  ngOnInit(): void {
  }

  scrollToTop() {
    this.scroll.scrollToPosition([0,0]);
  }
}
