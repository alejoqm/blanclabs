We need an application.properties on the current folder, where we are going to launch the JAR file.
    
    db.url: jdbc:postgresql:\/\/localhost:5432\/adjudication
    db.password: root
    db.user: postgres
    
    batch.file = insertBatchPostgres.sql
    
The batch file contains the INSERT to execute in the database.    

Run JAR file:
    java -jar AdjudicationPerformance-1.0-SNAPSHOT-shaded.jar
    
    
We can see the results of the execution in the BASH TERMINAL     