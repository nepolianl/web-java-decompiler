##Web Java Decompiler

**About Background**

The [Java Decompiler project](http://java-decompiler.github.io/) provides development and open source tools in order to compile and analyze Java 5 "byte code" and the latest version. It also provides additional bundles and plugin to compile and analyze Java "byte code":

**JD-GUI** - is a stand-alone graphical user interface that enables user interface to upload, browse Java source codes of ".class" to compile and analyze. It uses embedded [JD-Core](https://github.com/java-decompiler/jd-core) library to compile and analyze Java "byte code" from .class files. You can navigate around the reconstructed source code with the JD-GUI for instant access to methods and fields.

**JD-Core** - is a library written in Java that used to reconstruct Java source code from one or more ".class" files. JD-Core can be used to recover lost source code and explore the source code of Java runtime, third-party, and open source libraries. It supports latest features of Java from 1.5 until Java 1.10 such as annotations , generic or "enum" types. 

JD-Core, JD-GUI are open source projects released under the GPLv3 License.

**Motivation**

I have been using [Java Decompiler project](http://java-decompiler.github.io/) to compile and analyze Java "byte code" from ".class" files often among other open source Java decompilers. This is useful to navigate around the workflow, to understand the used implementation that consume and produce results especially in projects which integrated or embedded third-part, common or generic libraries. Thanks to [Java Decompiler project](http://java-decompiler.github.io/). 

While ago, I have been browsing for an online tool which can be used to perform the same operations online rather than doing it on desktop. Of-course, there are couple of tools which does this online but I have found none that provides convenient user interface to navigate and browse the reconstructed Java source from one or more ".class" files.

It had led me to think about developing a web application that enable responsive user interface to browse, upload one or more ".class" files to a web application back-end to compile and analyze Java "byte code" which in-turn returns the reconstructed Java source code.

As the result, I have developed a initial versions of web application [front-end](http://java-decompiler.github.io/) and [back-end](http://java-decompiler.github.io/) to support responsive user interface design to upload more or more ".class" files, displays Java source code of ".class" files, and to browse the reconstructed source code for instant access to method and fields.

##Getting Started

The Online Java Decompiler aims to develop web applications in order to support response user interfaces, and to decompile and analyze Java "byte code" from 1.5 until 1.10 versions.

**Scope**

The scope to develop a web application using bootstrap and angular as the front-end application, spring framework to develop back-end services that support processing multipart uploads come from front-end application and to manage reconstructed Java source code from ".class" using embedded database.

**Purpose**

The purpose of developing web applications to use features of [Java decompiler](http://java-decompiler.github.io/) open-source project online, and to understand Java decompiler core concepts and libraries. Therefore, to develop web applications got inspired from Java decompiler open-source project for personal use.

###Angular and Bootstrap

The [front-end](https://github.com/nepolianl/angular-bootstrap-java-decompiler) application that displays fancy user interface design to browse, drag and drop Java source code from one or more ".class". Displays the reconstructed Java source code in container and tabs for instant access to methods and fields using Angular concepts.

To getting started with the front-end application, please follow [here](https://github.com/nepolianl/angular-bootstrap-java-decompiler).

###Spring and Embedded Database

The [back-end](https://github.com/nepolianl/spring-java-core-decompiler) application developed to publish restful web services to receive and process multipart files, to provide reconstructed Java source from one or more ".class" files. It also supports processing ".jar" and ".zip" containing Java source in ".class" files.

To getting started with back-end application, please follow [here](https://github.com/nepolianl/spring-java-core-decompiler).

### Reference Documentation
For further reference, please consider the following sections:

* The official [Java decompiler](https://maven.apache.org/guides/index.html) project that aims to develop tools in order to decompile and analyze Java 5 “byte code” and the later versions.
* The open source code base for [Java decompiler](https://github.com/java-decompiler) project
* To get more help on the [Angular]() and [Angular CLI]() or use `ng help`
* To get more help on [bootstrap](https://getbootstrap.com/)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/#build-image)

