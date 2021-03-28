# JD-Core

It is a library written in Java that used to reconstruct Java source code from one or more ".class" files. JD-Core can be used to recover lost source code and explore the source code of Java runtime, third-party, and open source libraries. It supports latest features of Java from 1.5 until Java 1.10 such as annotations , generic or "enum" types. 

**Note**: The core implementation derived from Java decompiler open source library [Jd-Core](https://github.com/java-decompiler/jd-core)

### Description

JD-Core is a embedded JAVA library containing the JAVA decompiler of "Java Decompiler project" which can be used as integrated module. It support Java 1.1.8 to Java 12.0, including Lambda expressions, method references and default methods.

### Project setup

* Download source code `git clone git@github.com:nepolianl/jd-core.git`
* `cd jd-core`
* `mvn clean install`

## How to get started 

**Core Interface**

* Implement `public interface Decompiler` - to implement internal class type, Loader, and Printer
	
	ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();
	decompiler.decompile(loader, printer, "path/to/YourClass");
	String source = printer.toString();
	
* Implement `public interface Loader` - to implement specific Class loader to decompile
* Implement `public interface Printer` - to implement and collect reconstructed Java source code

**Dependency**

The core implementations were rewritten from Java decompiler project JD-Core
