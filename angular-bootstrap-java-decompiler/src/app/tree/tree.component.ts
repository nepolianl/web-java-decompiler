import { Component, OnInit } from '@angular/core';
import { SharedEventService } from '../service/shared-event.service';

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.scss']
})
export class TreeComponent implements OnInit {

  list: any = [];

  selected : any = null;
  
  constructor(private sharedEvent: SharedEventService) {
    this.sharedEvent.getJdTreeList().subscribe(treeList => {
      if (treeList != undefined) {
        treeList.forEach((tr: any) => {
          this.list.push(tr);
        });
      };
   });
   }

  ngOnInit(): void {
  }

  getIconClass(node: any) {
    return "ic-file";
  }

  toggleNode(node: any) {
    node.expand = !node.expand;
  }

  nodeClick(node: any) {
    // Reset the previous
    if (this.selected != null) {
      this.selected.selected = !this.selected.selected;
    }
  
    // Set the node as selected
    node.selected = !node.selected;
    this.selected = node;

    if (node.sourceId != undefined && node.className != undefined) {
      this.sharedEvent.selectionChange(this.selected);
    }
  }

}
