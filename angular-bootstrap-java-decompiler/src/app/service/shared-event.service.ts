import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedEventService {
  
  private jdTreeSelect = new BehaviorSubject(undefined);
  private jdTreeList = new BehaviorSubject(undefined);
  private jdCallbackListener = new BehaviorSubject(false);
  private percentageComplete = new BehaviorSubject(undefined);
  private jdSource = new BehaviorSubject(undefined);
  private navTabsToggle = new BehaviorSubject(undefined);

  constructor() { }

  getJdTreeList(): Observable<any> {
    return this.jdTreeList.asObservable();
  }

  updateJdTreeList(resp: any) {
    this.jdTreeList.next(resp.treeList);
  }

  getJdTreeSelect(): Observable<any> {
    return this.jdTreeSelect.asObservable();
  }

  selectionChange(sel: any) {
    this.jdTreeSelect.next(sel);
  }

  getJdCallbackListener(): Observable<boolean> {
    return this.jdCallbackListener.asObservable();
  }

  updateCallbackListener(status: boolean) {
    this.jdCallbackListener.next(true);
  }

  getUploadProgress(): Observable<number> {
    return this.percentageComplete.asObservable();
  }

  updateProgress(percent: number) {
    this.percentageComplete.next(percent);
  }

  getJdSource(): Observable<any> {
    return this.jdSource.asObservable();
  }

  updateSource(source : any) {
    this.jdSource.next(source);
  }

  getNavTabsToggle(): Observable<any> {
    return this.navTabsToggle.asObservable();
  }

  updateToggleNavTab(sel: any) {
    this.navTabsToggle.next(sel);
  }
}
