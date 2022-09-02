import {Component, Input, OnInit} from '@angular/core';
import {BrowserLocalStorage} from "../../../shared/storage/local-storage";
import {UsersService} from "../../../api/services/users.service";
import {take} from "rxjs";
import {UserInterface} from "../../../api/interfaces/user.interface";

@Component({
  selector: 'app-user-profile-card',
  templateUrl: './user-profile-card.component.html',
  styleUrls: ['./user-profile-card.component.scss']
})
export class UserProfileCardComponent implements OnInit {
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
    this.usersService.getUserInfo(this.userId)
      .pipe(take(1))
      .subscribe(response => {
        this.user = response;
        this.isUserCardReady = true;
      });
  }

}
