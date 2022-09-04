import {Component, HostListener, Input, OnInit} from '@angular/core';
import {BrowserLocalStorage} from "../../../shared/storage/local-storage";
import {OrdersService} from "../../../api/services/orders.service";
import {OrderInterface} from "../../../api/interfaces/order.interface";
import {take} from "rxjs";
import {OrdersRequestInterface} from "../../../api/interfaces/requests/orders-request.interface";

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.scss']
})
export class UserOrdersComponent implements OnInit {
  orders: OrderInterface[] = [];
  isOrdersListEmpty: boolean = false;

  currentPage: number = 1;
  pageSize: number = 3;
  currentPosition =  window.pageYOffset;

  constructor(
    private readonly localStorage: BrowserLocalStorage,
    private readonly ordersService: OrdersService
  ) { }

  ngOnInit(): void {
    this.showNextOrders();
  }

  // for window scroll events to get next user orders when the user will scroll down the page
  @HostListener('window:scroll', ['$event'])
  onScroll(e: HTMLElement) {
    let scroll =  window.pageYOffset;
    if (scroll > this.currentPosition) {
      this.showNextOrders();
    }
    this.currentPosition = scroll;
  }

  private showNextOrders() {
    let requestObject = this.getRequestObject();
    this.ordersService.getUserOrders(requestObject)
      .pipe(take(1))
      .subscribe(response => {
        // if the user has no orders at all
        if(response.length == 0 && this.currentPage == 1) {
          this.isOrdersListEmpty = true;
        }
        this.orders.push(...response)
        this.currentPage++;
      });
  }

  private getRequestObject(): OrdersRequestInterface {
    return {
      userId: this.localStorage.getCurrentUserId(),
      currentPage: this.currentPage - 1,
      pageSize: this.pageSize
    }
  }
}
