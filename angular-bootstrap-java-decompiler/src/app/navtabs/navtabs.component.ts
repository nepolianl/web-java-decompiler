import { Component, OnInit } from '@angular/core';
import { SharedEventService } from '../service/shared-event.service';

@Component({
  selector: 'app-navtabs',
  templateUrl: './navtabs.component.html',
  styleUrls: ['./navtabs.component.scss']
})
export class NavtabsComponent implements OnInit {
  tabs : any = [];
  treeSel : any = {};

  constructor(private sharedEvent: SharedEventService) { }

  ngOnInit(): void {
    this.sharedEvent.getJdTreeSelect().subscribe(select => this.openTab(select));
  }

  closeTab(tab : any) {
    let wasActive = tab.active;
    let index = this.tabs.indexOf(tab);
    let nextTab = this.tabs[index + 1];
    if (nextTab == undefined) {
      nextTab = this.tabs[index -1];
    }
    this.tabs.splice(index, 1);

    if (wasActive && nextTab != undefined) {
      nextTab.active = true;
    }
  }

  toggleTab(tab: any) {
    tab.active = !tab.active ? true : tab.active;
    this.tabs.forEach(t => {
      if (t.title != tab.title) {
        t.active = false;
      }
    });
    this.treeSel = tab.node;
  }

  openTab(node: any) {
    if (node != undefined && node.className != undefined) {
      let file = node.className;
      let title = (file.endsWith(".class")) ? file : file +".class";
      let tab = this.tabs.find(t => t.title === title);
      if (tab == undefined) {
        tab = {title: title, active : false, close: false};
        this.tabs.push(tab);
      }
      tab.node = node;
      tab.active = true; // focus
      //tab.scrollTo = node.scrollTo; // scrollTo
      // unselect other tabs
      this.tabs.forEach(t => {
        if (t.title != tab.title) {
          t.active = false;
        }
      });

      //scrollTo in the editor
      //alert(node.file +", " + node.scrollTo);
      this.treeSel = node;
    }
    
  }
}
