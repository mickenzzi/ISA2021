import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router'; 

@Component({
  selector: 'app-cottage-owner-cottage-list',
  templateUrl: './cottage-owner-cottage-list.component.html',
  styleUrls: ['./cottage-owner-cottage-list.component.css']
})
export class CottageOwnerCottageListComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }


}
