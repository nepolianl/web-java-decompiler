# Web Service for Java Decompiler

It is a back-end service implemented using spring web and spring data for managing and controlling restful services, embedded H2 database to store temporary processing code units.The application performs the following tasks:

* Publish restful web services from `@Controller` and `@RestController` classes
* Spring-data to create and manage domain class from `@Entity`
* Developed customized Multipart Loader to read and process ".class" files as byte[]
* Integrate with Jd-Core library
* Spring-boot with embedded tomcat web server

### Technologies Used

* Spring-boot 2.X
* Spring Web 5.3.5
* Spring Data 5.3.5
* H2 Database 1.1.13
* Java 1.11

## Project setup

* Download source code `git clone git@github.com:nepolianl/jd-web.git`
* `cd jd-web`
* `mvn clean install`
* Start @SpringbootApplication for running from IDE
* Execute `java -jar jd-web.jar` - to boot from terminal

**Project Configuration**

The service configurations are updated in application.properties for server, database, h2 console and logger
	
	server.address=0.0.0.0
	server.port=9090
	
**Datasource configuration**

	spring.datasource.url=jdbc:h2:mem:jd_db
	spring.datasource.driveClassName=org.h2.Driver
	spring.datasource.username=sa Default username
	spring.datasource.password= # Default password
	spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

**H2 database and Console**
* Spring-data executes schema.sql and data.sql from src/main/resources 
* Disable the below to get executed scheme.sql and data.sql -  spring.jpa.hibernate.ddl-auto=none
* If a configuration set to execute entity mapping using hibernate configuration of spring.jpa.hibernate.ddl-auto=create, create-drop, update , validate, then schema.sql and data.sql files will be ignored by default
* To view and manage H2 console

	spring.h2.console.enabled=true # Enabling H2 Console
	spring.h2.console.path=/h2 # Custom H2 Console URL
	spring.h2.console.settings.trace=false # Whether to enable trace output. By default false is
	spring.h2.console.settings.web-allow-others=false # Whether to enable remote access. By default false is

* Then, the h2 database console can be accessed `http://localhost:9090/h2`

#API

* Request Method:

	Method: POST
	URL: http://localhost:9090/jd/api/upload
	Content-type: multipart-formdata; boundary=<calculated when request sent>

* Request Body (Form-data):
	
	files -> SomeClassImpl.class
	files -> SomeClassInterface.class
	files -> SomeClass$List.class

* Response

	{
	    "treeList": [
	        {
	            "name": "Decompiled List",
	            "icon": "",
	            "className": null,
	            "packageName": null,
	            "sourceId": null,
	            "children": [
	                {
	                    "name": "com",
	                    "icon": null,
	                    "className": null,
	                    "packageName": null,
	                    "sourceId": null,
	                    "children": [
	                        {
	                            "name": "com.neps",
	                            "icon": null,
	                            "className": null,
	                            "packageName": "com",
	                            "sourceId": null,
	                            "children": [
	                                {
	                                    "name": "SelectionSort.class",
	                                    "icon": "",
	                                    "className": null,
	                                    "packageName": null,
	                                    "sourceId": 3,
	                                    "children": [
	                                        {
	                                            "name": "SelectionSort",
	                                            "icon": "",
	                                            "className": "SelectionSort",
	                                            "packageName": "com.neps",
	                                            "sourceId": 3,
	                                            "children": [
	                                                {
	                                                    "name": "SelectionSort()",
	                                                    "icon": "",
	                                                    "className": "SelectionSort",
	                                                    "packageName": "com.neps",
	                                                    "sourceId": 3,
	                                                    "children": []
	                                                },
	                                                {
	                                                    "name": "main(String[]) : void",
	                                                    "icon": "",
	                                                    "className": "SelectionSort",
	                                                    "packageName": "com.neps",
	                                                    "sourceId": 3,
	                                                    "children": []
	                                                },
	                                                {
	                                                    "name": "getSortedString(int[]) : String",
	                                                    "icon": "",
	                                                    "className": "SelectionSort",
	                                                    "packageName": "com.neps",
	                                                    "sourceId": 3,
	                                                    "children": []
	                                                }
	                                            ]
	                                        }
	                                    ]
	                                }
	                            ]
	                        }
	                    ]
	                }
	            ]
	        }
	    ]
	}

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

