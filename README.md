# Mancala

## How to run:
1. From command line: "java -jar mancala-0.1.0.jar".
2. Launch 2 browser sessions point them to localhost:8080.
3. On the login page provide a username (no need for password), the user will be stored in memory.

## How to build:
After cloning the repo. use the following maven command:
```
mvn clean install
```

## Client-server communication:
The client-server communication is using websockets to push updates from the server to client. 
That is when a player makes a move, it is sent to the server, processed then pushed to the other player.
The client uses socketJS because it can handle browsers not supporting websockets in a transparent manner. While the server side is based on Spring websockets implementation. 
