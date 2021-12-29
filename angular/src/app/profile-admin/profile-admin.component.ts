import { Component, OnInit } from '@angular/core'
import { HttpErrorResponse} from '@angular/common/http';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-profile-admin',
  templateUrl: './profile-admin.component.html',
  styleUrls: ['./profile-admin.component.css']
})
export class ProfileAdminComponent implements OnInit {
  public users: User[];
  constructor(private userService: UserService)
	{
		this.users = [];
	}

  ngOnInit(): void{
		this.getAllUsers();
	}
	
	public getAllUsers(): void{
		this.userService.getAllUsers().subscribe(
		 (response: User[]) => {
			 this.users = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}

}
