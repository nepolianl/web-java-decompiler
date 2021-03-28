import { Component, Input, OnInit, SimpleChange } from '@angular/core';
import { SharedEventService } from '../service/shared-event.service';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.scss']
})
export class EditorComponent implements OnInit {

  @Input() treeSel : any = {};
  htmlText : any = {};

  constructor(private sharedEvent : SharedEventService) { }

  ngOnInit(): void {
    this.sharedEvent.getJdSource().subscribe(resp => {
      if (resp != undefined && resp.source != null) {
        this.htmlText = resp.source;
      }
    });
  }

  ngOnChanges(changes: SimpleChange) {
    if (this.treeSel != undefined && this.treeSel.sourceId != undefined) {
      this.sharedEvent.updateToggleNavTab(this.treeSel);
    }
  }

}
