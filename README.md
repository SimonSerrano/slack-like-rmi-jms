# Slack Like RMI/JMS

## AUTHOR :

  * Simon Serrano ([mail](mailto:simon.serrano@hotmail.fr))

## DESCRIPTION

Slack like console application in Java RMI and JMS. Main features are :
  * Login (simulated)
  * Group subscription
  * Group listening (being able to see the messages in a group)
  * Sending messages to a group
  
Careful, it is not a Maven Project.
 
## DEPENDENCIES

### Server
  * [ActiveMQ Core](https://mvnrepository.com/artifact/org.apache.activemq/activemq-core)
  * [ActiveMQ Broker](https://mvnrepository.com/artifact/org.apache.activemq/activemq-broker)
  * [SLF4J API](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)
  * [SLF4J LOG4J](https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12)
  
### Client
  * [ActiveMQ Core](https://mvnrepository.com/artifact/org.apache.activemq/activemq-core)
  * [SLF4J API](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)
  * [SLF4J LOG4J](https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12)

  
## ARCHITECTURE

The GitHub is divided in two different modules. One is the server, the other is the client.
 The server hosts the broker for JMS as well as the RMI backend where the objects are located (Group, Server and User).
 The client is a simple console reading lines in the standard input and reacting on the given command.
 A group creates a topic where it is subscribed for messages and then publishes all the messages to the 
 subscribers (actual users) on their own topic for the group. Thus, every user have access to the messages
 in their subscribed groups as long as the server keeps running. The topic a group is listening on is the group's name.
 It publishes on every user's topic (created at subscription with the group's name concatenated with the user's name)
 on a message received. The client is listening, through the connection established at a user's login action,
  on a group with the topic defined at the subscription, thus reads the group's messages.
 
## GETTING STARTED

Using `maven package` to compile the project at the base of the directory. Then, in each module in the target directory
launch the jar using `java` command.

You can also load the project in an IDE and start it from there, but the SLF4J library throws an exception with the 
embedded maven of the IDE (usually eclipse or intelliJ).


 
## USAGE

Here are the commands from the client console :


___
`!connect` 
  
Connect to the server with a username and a password.
___
`!groups`

List all the available groups.
___
`!subscribe [group-name]`

Subscribe to a group.
___
`!listen [group-name]`

Listen to a group conversation.
___
`!quiet [group-name]`

Stop listenning to a group conversation.
___
`!publish [group-name] [...message]`

Publish a message to a group.
___
`!help`

Show the help menu.
___
`!exit`

Exit the program.
___


