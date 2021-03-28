import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { HttpClientModule } from  '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProgressComponent } from './progress/progress.component';
import { FiledragdropComponent } from './filedragdrop/filedragdrop.component';
import { FiledragdropDirective } from './directives/filedragdrop/filedragdrop.directive';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { DefaultComponent } from './default/default.component';
import { FileuploadComponent } from './fileupload/fileupload.component';
import { WorkspaceComponent } from './workspace/workspace.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { EditorComponent } from './editor/editor.component';
import { TreeComponent } from './tree/tree.component';
import { NavtabsComponent } from './navtabs/navtabs.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    DashboardComponent,
    ProgressComponent,
    FiledragdropComponent,
    FiledragdropDirective,
    ToolbarComponent,
    DefaultComponent,
    FileuploadComponent,
    WorkspaceComponent,
    SidenavComponent,
    EditorComponent,
    TreeComponent,
    NavtabsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
