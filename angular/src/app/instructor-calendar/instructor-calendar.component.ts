import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Termin } from '../model/termin';
import { Adventure } from '../model/adventure';
import { UserService } from '../service/user.service';
import { AdventureService } from '../service/adventure.service';
import { HttpErrorResponse} from '@angular/common/http';
@Component({
  selector: 'app-instructor-calendar',
  templateUrl: './instructor-calendar.component.html',
  styleUrls: ['./instructor-calendar.component.css']
})
export class InstructorCalendarComponent implements OnInit {
  id!: number;
  idTermin: number=0;
  public termins: Termin[];
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private adventureService: AdventureService,
  ) {
		this.termins = [];
	  }

  ngOnInit(): void {
	this.id = this.route.snapshot.params['id'];
	this.getAllTermins();
  }
  
  goBack(): void{
	this.router.navigate(['/homeInstructor',this.id]);
  }
  
  public deleteTermin(idTermin1?: number): void{
	if(idTermin1 === undefined){
		alert('Id ne postoji');
	}
	else{
		this.idTermin = idTermin1;
		this.adventureService.deleteTermin(this.idTermin).subscribe(
		 (response) => {
			alert('Uspesno ste izbrisali termin');
			this.getAllTermins();
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
  }
  
   public getAllTermins(): void{
		this.adventureService.getAllTermins(this.id).subscribe(
		 (response: Termin[]) => {
			 this.termins = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}

}
