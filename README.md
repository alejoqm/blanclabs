### Project: adjudication-service ###

### What is this repository for? ###

* This repository was created from the archetype project.

### How do I get set up? ###

* Configuration instructions (optional)
	* Configure port: see the file application.properties in the resources folder and update the server.port attribute.
	
* Deployment instructions
	* Build: mvn clean install -U
	* Run: mvn spring-boot:run
	* Note: you can use mvnw instead mvn command to use a specific maven wrapper.

* View endpoints with the Swagger UI
	* http://service-domain:port/swagger-ui.html

* How to run tests
	* Run Unit Tests: mvn test
	
* For local test

    1. create a docker network 
    	sudo docker network create -d bridge roachnet
    
    2. start first node
    
    sudo docker run -d \
    --name=roach1 \
    --hostname=roach1 \
    --net=roachnet \
    -p 26257:26257 -p 9393:8080\
    -v "${PWD}/cockroach-data/roach1:/cockroach/cockroach-data"  \
    cockroachdb/cockroach:v2.1.4 start --insecure
    
    3. add additional nodes
    
    sudo docker run -d \
    --name=roach2 \
    --hostname=roach2 \
    --net=roachnet \
    -v "${PWD}/cockroach-data/roach2:/cockroach/cockroach-data" \
    cockroachdb/cockroach:v2.1.4 start --insecure --join=roach1
    
    //additional node
    
    sudo docker run -d \
    --name=roach3 \
    --hostname=roach3 \
    --net=roachnet \
    -v "${PWD}/cockroach-data/roach3:/cockroach/cockroach-data" \
    cockroachdb/cockroach:v2.1.4 start --insecure --join=roach1
    
    //
    4. enter sql cli
    	sudo docker exec -it roach1 ./cockroach sql --insecure
    
    5. Create a database
    
    	CREATE DATABASE adjudicationdb;
    
    6. if you need to drop database 
    	DROP DATABASE adjudicationdb cascade;	
 
 *  Consul local config
     * sudo docker run -d --name=consul1 -p 8500:8500 consul agent -dev -client=0.0.0.0 -bind=0.0.0.0
     * Then enter consul UI http://localhost:8500 and create key/value config/translation-service/test=value  	
     * Use consul-imex to export and import properties to consul.
             *  Export all properties: sudo docker run -it -v pwd:/consul-imex sozpinar/consul-imex export -u localip:8500 -p / consulProperties.json
             *  Copy file from docker to file system: sudo docker cp `sudo docker ps -ql`:/consul-imex/consulProperties.json .
             *  Import properties from file: sudo docker run -it -v `pwd`/consulProperties.json:/consulProperties.json sozpinar/consul-imex import -u localip:8500 -p / -v /consulProperties.json
      
 *  For more information about Adjudication service
	 * https://outsideiq.jira.com/wiki/spaces/OIQ/pages/498466928/Adjudication+Service
    	