import {Component, Input, OnChanges, OnInit, SimpleChange, SimpleChanges} from '@angular/core';
import {UserInterface} from "../../api/interfaces/user.interface";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";
import {UsersService} from "../../api/services/users.service";
import {take} from "rxjs";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.scss']
})
export class UserCardComponent implements OnInit, OnChanges {
  @Input() userId: number = 0;
  user: UserInterface = {
    id: 0,
    fullName: '',
    email: '',
    dateOfBirth: '',
    username: '',
    ordersCount: 0
  };
  isUserCardReady: boolean = false;

  constructor(
    private readonly localStorage: BrowserLocalStorage,
    private readonly usersService: UsersService
  ) { }

  ngOnInit(): void {
    if(this.userId == 0) { // didn't pass Input param userId
      // it means we show user info on user profile page
      this.userId = this.localStorage.getCurrentUserId();
    }
    // otherwise - on admin profile page
    this.loadInfo(this.userId);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['userId'] != undefined) {
      let change: SimpleChange = changes['userId'];
      let userId: number = change.currentValue;
      this.loadInfo(userId);
    }
  }

   private loadInfo(userId: number) {
     this.usersService.getUserInfo(userId)
       .pipe(take(1))
       .subscribe(response => {
         this.user = response;
         this.isUserCardReady = true;
       });
   }
}
