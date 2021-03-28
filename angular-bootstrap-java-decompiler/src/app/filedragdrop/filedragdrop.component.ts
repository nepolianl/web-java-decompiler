import { Component, OnInit, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-filedragdrop',
  templateUrl: './filedragdrop.component.html',
  styleUrls: ['./filedragdrop.component.scss']
})
export class FiledragdropComponent implements OnInit {

  @ViewChild("fileDropRef", { static: false }) fileDropEl: ElementRef;
  files: any[] = [];
  
  @Output() filesDroppedEvent = new EventEmitter<any[]>();

  constructor() { }

  ngOnInit(): void {
  }

  /**
   * on file drop handler
   */
  onFileDropped($event) {
    this.prepareFilesList($event);
  }

  /**
   * handle file from browsing
   */
  fileBrowseHandler(files) {
    this.prepareFilesList(files);
  }

  /**
   * Convert Files list to normal array list
   * @param files (Files List)
   */
  prepareFilesList(files: Array<any>) {
    for (const item of files) {
      item.progress = 0;
      this.files.push(item);
    }
    this.filesDroppedEvent.emit(this.files);
    this.fileDropEl.nativeElement.value = "";
  }

}
