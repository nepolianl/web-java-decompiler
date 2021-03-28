import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FileService } from '../service/file.service';
import { SharedEventService } from '../service/shared-event.service';

@Component({
  selector: 'app-fileupload',
  templateUrl: './fileupload.component.html',
  styleUrls: ['./fileupload.component.scss']
})
export class FileuploadComponent implements OnInit {

  @Input() files: any[] = [];

  constructor(private fileService : FileService, private sharedEvent: SharedEventService) {
    this.sharedEvent.getUploadProgress().subscribe(percent => {
      if (percent != undefined) this.files.forEach(file => file.progress = percent);
    });
   }

  ngOnInit(): void {
    this.uploadFiles(this.files);
  }

  uploadFiles(files: any[]) {
    this.fileService.uploadFiles(files);
  }

  /**
   * Delete file from files list
   * @param index (File index)
   */
  deleteFile(index: number) {
    if (this.files[index].progress < 100) {
      console.log("Upload in progress.");
      return;
    }
    this.files.splice(index, 1);
  }
  
  /**
   * format bytes
   * @param bytes (File size in bytes)
   * @param decimals (Decimals point)
   */
  formatBytes(bytes, decimals = 2) {
    if (bytes === 0) {
      return "0 Bytes";
    }
    const k = 1024;
    const dm = decimals <= 0 ? 0 : decimals;
    const sizes = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + " " + sizes[i];
  }

}
