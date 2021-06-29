# axby Client application

Client application for the game 5-in-a-row.

### Pre-Requisites

1. Java 8


###  To Run Locally

1. cd to the project directory.
2.  `./mvnw package spring-boot:repackage -DskipTests` creates runnable jar using maven Springboot plugin.
    
Run the below step in two terminals with different ports to mimic two players in the game. 
3. `java -jar target/axbyClient-0.0.1-SNAPSHOT-spring-boot.jar --server.port=8083` launches the Client on port 8083.

   `java -jar target/axbyClient-0.0.1-SNAPSHOT-spring-boot.jar --server.port=8084` launches the Client on port 8084.

NOTE : Please make sure that axbyServer app is started before playing.

