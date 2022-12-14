# Oso Proof of Concept

This repo can be run as long as you have Docker, Java 17 and Gradle installed

Clone the repo and then run 

1. `docker compose up -d` this will launch a postgres instance
2. `./gradlew build` to compile the application
3. `./gradlew app:run` to launch the app.

At the moment, two repositories are created on startup, 1 public and 1 private.  

Expected behavior is that you should be able to go to `localhost:8080/repo?name=test` and get a valid response, as this is a public repo.  At the moment this does not work :( 
