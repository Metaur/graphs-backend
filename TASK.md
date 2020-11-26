#### Functional requirements:
Create an application that implements storing data in a graph and exposes some logics via
REST calls.

Implement the following REST calls:
1. Add an edge, connecting two vertices (there can be multiple edges between two given
vertices, edge can also connect a vertex to itself, i.e. a loop).
2. Remove all edges, connecting two given vertices
3. Check if the graph is connected, i.e. there is a path from any vertex to any other vertex
4. Retrieve the whole graph structure as a JSON

Add unit tests.

#### Non-functional requirements: 
Java 11, Spring Boot as a framework, Maven as a build tool

#### Deliverables: 
a link to github or gitlab repository