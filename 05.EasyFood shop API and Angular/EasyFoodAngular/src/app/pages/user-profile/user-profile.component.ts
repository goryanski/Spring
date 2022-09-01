import { Component, OnInit } from '@angular/core';
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {OrderInterface} from "../../api/interfaces/order.interface";
import {OrdersService} from "../../api/services/orders.service";
import {take} from "rxjs";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  constructor() { }
  ngOnInit(): void {}
}
