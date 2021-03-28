import { Component, OnInit } from '@angular/core';
import { SharedEventService } from '../service/shared-event.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  jdCallback : boolean = false;

  constructor(private sharedEvent : SharedEventService) { }

  ngOnInit(): void {
    this.sharedEvent.getJdCallbackListener().subscribe(state => {
      this.jdCallback = state; 
    });
  }

}
