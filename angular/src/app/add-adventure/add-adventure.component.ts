import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse} from '@angular/common/http';
import { AdventureService } from '../service/adventure.service';
import { Adventure } from '../model/adventure';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-add-adventure',
  templateUrl: './add-adventure.component.html',
  styleUrls: ['./add-adventure.component.css']
})
export class AddAdventureComponent implements OnInit {
  id!: number;
  URL_ss = "";
  URL: string = "";
  URL_R: string = "";
  URL_path : string = "/assets/img/";
  adventure: Adventure = {
	id: 0,
	title: '',
	description: '',
	address: '',
	image: '',
	maxNumber: 0,
	instructorBiography: '',
	rule: '',
	equipment: '',
	priceList: 0,
	cancelCondition: '',
  }
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private adventureService: AdventureService
  ) { }

  ngOnInit(): void {
	this.id = this.route.snapshot.params['id'];
  }
  
  goBack(){
	this.router.navigate(['/homeInstructor', this.id]);
  }
  
  addAdventure(): void{
	  this.URL_ss = this.URL.substring(12);
	  this.URL_R = this.URL_path + this.URL_ss;
	  const data = {
      title: this.adventure.title,
      description: this.adventure.description,
      address: this.adventure.address,
      image: this.URL_R,
      maxNumber: this.adventure.maxNumber,
      instructorBiography: this.adventure.instructorBiography,
      rule: this.adventure.rule,
      equipment: this.adventure.equipment,
      priceList: this.adventure.priceList,
      cancelCondition: this.adventure.cancelCondition
    }
	this.adventureService.createAdventure(data,this.id)
	.subscribe(
        response => {
			alert('Uspesno ste dodali avanturu');
			this.router.navigate(['/homeInstructor',this.id]);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }

      );
  }
 

}
