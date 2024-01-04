# Greenhouse (Java Spring-Boot)

### Requirements

The project has the following dependencies:
* JRE/JDK 11 [Download from here](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
* Maven 3.5+ [Download from here](http://maven.apache.org/download.cgi) | [Install guide](https://www.baeldung.com/install-maven-on-windows-linux-mac)

### Project structure

This is a multi-module maven project relying on the [parent `pom.xml`](https://github.com/arrowhead-f/client-skeleton-java-spring/blob/master/pom.xml) which lists all the modules and common dependencies.

##### Modules:

* **controller-consumer**: application module with the purpose of initiating an orchestration request and consume the services from the chosen providers. This consumes the `soilsensor-provider` and `lightsensor-provider` services. Activating the lights and watering systems through the hardwaresystems-provider as necessary.

* **soilsensor-provider**: application module with the purpose of registering a soil sensor service into the Service Registry and running a web server where the services measured data is available.

* **lightsensor-provider**: application module with the purpose of registering a light sensor service into the Service Registry and running a web server where the services measured data is available.

* **hardwaresystems-provider**: aplication module with the purpose of registering a hardware systems service where the hardware systems can be activated.


### This project is built on the arrowhead java skeleton
https://github.com/arrowhead-f/client-skeleton-java-spring

### How to run the application
on first initialization you will need to first set up and run the three core systems (Service Registry, Authorization, and Orchestration). This can easely be done by using the arrowhead startup tools. [Download from here](https://www.aitia.ai/products/arrowhead-tools/)

You then have to manually create some entries in the arrowhead manager tool. You will have to create the controller service in the service registry as this is not done in the code. You then have to add the appropriate authorization rules so that the controller-consumer have access to the providers and their service definitions.
Lastly you have to add the appropriate orchestration rules so that the controller-consumer can access the other services through the orchestrator.