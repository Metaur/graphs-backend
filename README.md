### 28 Stones home assignment

#### Prerequisites

- Java 11

#### How to start

- `./mvnw spring-boot:run` to start the server
- `./mvnw clean build` to build the project
- `./mvnw test` to run the tests

#### About

The service handles the graph, its verteces and edges via adjacency list representation.

Sample API calls are available [here](./docs/apiCalls.http)

#### TODO

-[x] Setup spring boot application
-[x] Create domain model
-[x] Create rest api methods
    -[x] retrieve a graph as a JSON
-[x] Services and business logic
    -[x] create an edge
    -[x] create a loop
    -[x] remove all edges between 2 vertices
    -[x] check if 2 vertices connected (path between exists)    
-[x] Tests 

##### 'Would be great to have' things

-[ ] API call caching
-[ ] API to manage verteces (create detached vertex, delete, delete and detach)
-[ ] do not reinvent the wheel and use any library for graph modelling (guava?)
-[ ] Use Neo4j (graph database) for persistence
-[ ] Dockerize server, use docker compose to orchestrate server and dbs