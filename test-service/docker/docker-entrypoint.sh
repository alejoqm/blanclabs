#!/bin/sh

cp /etc/ssl/certs/java/cacerts /app/cacerts
chmod 777 /app/cacerts

keytool -importcert -storepass changeit  -noprompt -alias local-CA -keystore /app/cacerts -file /app/consul-ca.crt

java -Djavax.net.ssl.trustStore=/app/cacerts  -jar /app/test-service.jar --spring.cloud.bootstrap.location=file:/app/bootstrap.properties

