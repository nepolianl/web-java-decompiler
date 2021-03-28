import { Component, OnInit } from '@angular/core';
import { SharedEventService } from '../service/shared-event.service';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss']
})
export class DefaultComponent implements OnInit {
  
  filesCount : number = 0;
  files : any[] = [];
  
  fileUploadCallback : boolean = false;

  constructor(private sharedEvent: SharedEventService) { }

  ngOnInit(): void {
    this.sharedEvent.getJdCallbackListener().subscribe(state => {this.fileUploadCallback = state});
  }

  filesDraggedEvent($event: any[]) {
    this.files = $event;
    this.filesCount = $event.length;
  }
}
