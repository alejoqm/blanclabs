### Project: Name ###

### What is this repository for? ###

* This repository was created from the archetype project.

### How do I get set up? ###

* Configuration instructions (optional)
	* Configure port: see the file application.properties in the resouartifactDescriptionrces folder and update the server.port attribute.
	
* Deployment instructions
	* Build: mvn clean install -U
	* Run: mvn spring-boot:run
	* Note: you can use mvnw instead mvn command to use a specific maven wrapper.

* View endpoints with the Swagger UI
	* http://service-domain:port/swagger-ui.html

* How to run tests
    * We need remove a fail() line from ApplicationTest to get a successful build of this service.
	* Run Unit Tests: mvn test
