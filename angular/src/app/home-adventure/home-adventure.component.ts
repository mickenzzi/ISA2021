import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Termin } from '../model/termin';
import { Request } from '../model/request';
import { Adventure } from '../model/adventure';
import { UserService } from '../service/user.service';
import { RequestService } from '../service/request.service';
import { AdventureService } from '../service/adventure.service';
import { HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home-adventure',
  templateUrl: './home-adventure.component.html',
  styleUrls: ['./home-adventure.component.css']
})
export class HomeAdventureComponent implements OnInit {
  id!: number;
  idAdventure!: number;
  adventure: Adventure = new Adventure();
  termin: Termin = new Termin();
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private requestService: RequestService,
	private adventureService: AdventureService,
  ) { }

  ngOnInit(): void {
	this.id = this.route.snapshot.params['id'];
	this.idAdventure = this.route.snapshot.params['idAdventure'];
	this.getAdventure();
  }
  
  goBack(): void{
	this.router.navigate(['/homeInstructor', this.id]);
  }
  
  public getAdventure(): void{
		this.adventureService.getAdventure(this.idAdventure).subscribe(
		 (response: Adventure) => {
			 this.adventure = response;
		 }
		);
  }
  
  editAdventure(): void{
	this.adventureService.updateAdventure(this.adventure).subscribe(
			response=>{
				alert('Izmenili ste avanturu.');
				this.getAdventure();
			},
			(error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
  }
  
  createAction(): void{
	  this.adventureService.createAction(this.id,this.idAdventure,this.termin).subscribe(
			response=>{
				alert('Uspesno ste kreirali termin za brzu rezervaciju.');
			},
			(error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
  }

}
