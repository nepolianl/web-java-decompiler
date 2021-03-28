import { HttpEventType } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestClientService } from './rest-client.service';
import { SharedDataService } from './shared-data.service';
import { SharedEventService } from './shared-event.service';
import { environment as env } from 'src/environments/environment';

const DECOMPILE_URL = env.SERVER_URL + env.UPLOAD_API;
const SOURCE_URL = env.SERVER_URL + env.SOURCE_API;

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private restClient : RestClientService, private sharedEvent : SharedEventService, private sharedData: SharedDataService) {
    this.sharedEvent.getJdTreeSelect().subscribe(sel => {
      if (sel != undefined && sel.sourceId != undefined) {
        this.loadSource(sel.sourceId);
      }
    });
    this.sharedEvent.getNavTabsToggle().subscribe(sel => {
      if (sel != undefined && sel.sourceId != undefined) {
        this.loadSource(sel.sourceId);
      }
    });
  }

  loadSource(sourceId: number) {
    let source = this.sharedData.getDecompiledText(sourceId);
    if (source != undefined) {
      this.sharedEvent.updateSource({sourceId : sourceId, source : source});
    } else {
      this.getSource(sourceId);
    }
  }

  uploadFiles(files : any[]) {
    const data : any = new FormData();
    for (let i =0; i <files.length; i++) {
      data.append('files', files[i]);
    }

    this.restClient.post(DECOMPILE_URL, data).subscribe(
      resp => this.callback(resp),
      err => {
        console.log(err)
      });
  }

  getSource(sourceId: number) {
    let queryParams = { "sourceId": sourceId };

    this.restClient.get(SOURCE_URL, queryParams).subscribe(
      resp => this.sharedEvent.updateSource(resp),
      err => console.log(err)
    );
  }

  callback(resp: any) {
    if (resp.type === HttpEventType.Response) {
      this.sharedEvent.updateJdTreeList(resp.body);
      this.sharedEvent.updateCallbackListener(true);
    }

    if (resp.type === HttpEventType.Sent) {
      let percent = 5;
      this.sharedEvent.updateProgress(percent);
    }

    if (resp.type === HttpEventType.UploadProgress) {
      let percent = Math.round(100 * resp.loaded / resp.total);
      this.sharedEvent.updateProgress(percent);
    }

    if (resp.type === HttpEventType.ResponseHeader && resp.statusCode === 200) {
      let percent = Math.round(100 * resp.loaded / resp.total);
      this.sharedEvent.updateProgress(percent);
    }
  }

}
