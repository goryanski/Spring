import { Component, OnInit } from '@angular/core';
import {AdministrateUsersService} from "../../api/services/administrate-users.service";
import {take} from "rxjs";
import {BrowserLocalStorage} from "../../shared/storage/local-storage";

@Component({
  selector: 'app-users-control',
  templateUrl: './users-control.component.html',
  styleUrls: ['./users-control.component.scss']
})
export class UsersControlComponent implements OnInit {
  userId: number = 0;
  isUserIdReceived: boolean = false;
  userNotFound: boolean = false;
  isThisCurrentUser: boolean = false;


  constructor(
    private readonly administrateUsersService: AdministrateUsersService,
    private readonly localStorage: BrowserLocalStorage
  ) { }

  ngOnInit(): void {

  }

  btnSearchUserClick(username: string) {
    if(username !== '') {
      this.administrateUsersService.getUserIdByUsername(username)
        .pipe(take(1))
        .subscribe(userId => {
          this.userId = userId != 0 ? userId : 0;
          this.isUserIdReceived = userId != 0;
          this.userNotFound = userId == 0;
          this.isThisCurrentUser = userId == this.localStorage.getCurrentUserId()
        });
    }
  }

  onBlockUserClick() {
    this.administrateUsersService.blockUser(this.userId)
      .pipe(take(1))
      .subscribe(response => {
        if(response.message === "OK") {
          alert('User has been blocked successfully');
        } else {
          alert(response.message);
        }
      });
  }

  onSetAdminClick() {
    this.administrateUsersService.setUserAsAdmin(this.userId)
      .pipe(take(1))
      .subscribe(response => {
        if(response.message === "OK") {
          alert('User has been set as admin successfully');
        } else {
          alert(response.message);
        }
      });
  }
}
