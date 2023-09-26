# Adding Access Control and Authentication to an HTTP Server

For this task you will modify a http server program to provide authentication and access control for its various services.


## Setup

To run these programs you will need to install a recent version of Java, preferably version 17
or higher, and also the Gradle build tool https://gradle.org/.

With those you're ready to run the programs.


## Running the programs

First we need to start the server by running the following from a terminal in the server folder:

```sh
gradle run
```

After installing the required libraries, it will say `> :app:run`, which means you are ready to start the client by running
the following from a terminal in the root of this java folder:

```sh
java Client.py
```

The client goes through and tests each of the example endpoints of the server.

# Your Task

You will add an authentication mechanism and access control to the server as specified in the assignment specs,
for this you will modify the Controller class found in `server/app/src/main/java/server/Controller.java`.
You will also extend the client program (found at `Client.java`), to test each possible responses from the server.