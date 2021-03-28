# Spring with JD-Core for Java Decompiler

This is a spring-boot multi-module maven project with spring web module and integrated [JD-Core]() module to publish restful web services to receive and process multipart files, to provide reconstructed Java source from one or more ".class" files. It also supports processing ".jar" and ".zip" containing Java source in ".class" files. The following tasks will be taken care in the main module:
	
* Spring-boot Maven module project with [JD-Web](https://github.com/nepolianl/spring-java-core-decompiler) and [JD-Core](https://github.com/nepolianl/spring-java-core-decompiler)
* Apache Tomcat - Embedded web server
* Spring Web - to publish restful web services
* Embedded H2 Database - to store reconstructed Java source code temporarily for a session
* JD-Core - Integrate Java decompiler core library

## Getting Started

* Download the source code from `git clone git@github.com:nepolianl/spring-java-core-decompiler.git`
* `cd spring-java-core-decompiler`
* Compile using maven `mvn clean install`
	
	[INFO] ------------------------------------------------------------------------
	[INFO] Reactor Summary for spring-java-core-decompiler 1.0:
	[INFO]
	[INFO] spring-java-core-decompiler ........................ SUCCESS [  1.500 s]
	[INFO] jd-core ............................................ SUCCESS [ 15.634 s]
	[INFO] jd-web ............................................. SUCCESS [  7.501 s]
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time:  25.178 s
	[INFO] Finished at: 2021-03-28T15:45:26+05:30
	[INFO] ------------------------------------------------------------------------
	
* Start application `java -jar spring-java-core-decomplier.jar`
* Access the web services using `http://localhost:9090/jd/api`

## Read more

* To get more understanding about [JD-Web](https://github.com/nepolianl/spring-java-core-decompiler) - a spring backend project to publish web services and to manage embedded database 
* To get more understanding about [JD-Core](https://github.com/nepolianl/spring-java-core-decompiler) - a integrated Java decompiler core library

## Reference Documentation
For further reference, please consider the following sections:

* The official [Java decompiler](https://maven.apache.org/guides/index.html) project that aims to develop tools in order to decompile and analyze Java 5 “byte code” and the later versions.
* The open source code base for [Java decompiler](https://github.com/java-decompiler) project
* To get more help on the [Angular]() and [Angular CLI]() or use `ng help`
* To get more help on [bootstrap](https://getbootstrap.com/)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/#build-image)

