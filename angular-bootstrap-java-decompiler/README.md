# Angular UI for Java Decompiler

It is a front-end application to browse, drag-drop one or more ".class" files, to display reconstructed Java source code, and to instant navigate between decompiled classes, methods and fields including Java generic and enum types. The following features are supported:

* Upload one or more ".class" Java files
* Drag and drop files
* Display reconstructed source code
* Navigate between classes, methods and fields 
* Responsive UI

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 10.1.1.

## Technologies Used

* Angular Core 10.1.1
* Angular CLI 10.1.1
* Bootstrap 4.5.0
* Ng Bootstrap 8.0.4
* Font Awesome 5.15.3
* SCSS

## Project setup

* Download source code `git clone git@github.com:nepolianl/angular-bootstrap-java-decompiler.git`
* `cd angular-bootstrap-java-decompiler`
* `npm install`
* `npm build` - to build the project
* `npm start` or `ng serve` to startup the application
* Access the application using `http://localhost:4200/`

**Configuration**

The server configurations are applied in environment files inside src/environments/environment.ts

	export const environment = {
	  production: false,
	  SERVER_URL: 'http://localhost:9321/service/api',
	  GET_ORDERS_API: '/getAllWebOrders',
	  CREATE_ORDER_API: '/createWebOrder'
	};

## Examples: User Interface

**Dashboard**
![Alt text](./screenshots/dashboard.JPG?raw=true "Dashboard Screen")

**Drag and Drop** and **File Upload**
![Alt text](./screenshots/upload.JPG?raw=true "File Upload Screen")

**View Source** and **Navigation**
![Alt text](./screenshots/view.JPG?raw=true "Navigation Screen")


## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
