import { Injectable } from '@angular/core';
import { SharedEventService } from './shared-event.service';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {

  private sourceMap = new Map();

  constructor(private sharedEvent: SharedEventService) {
    sharedEvent.getJdSource().subscribe(resp => {
      if (resp != undefined) {
        this.sourceMap.set(resp.sourceId, resp.source)
      }
    });
  }

  getDecompiledText(sourceId: number) {
    return this.sourceMap.get(sourceId);
  }

}
