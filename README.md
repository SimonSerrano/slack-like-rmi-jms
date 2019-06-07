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

  * [JMS](https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html)
  * [ActiveMQ](https://activemq.apache.org/using-activemq)

  
## ARCHITECTURE

The GitHub is divided in two different modules. One is the server, the other is the client.
 The server hosts the broker for JMS as well as the RMI backend where the objects are located.
 The client is a simple console reading lines in the standard input and reacting on the command it is given.
 A group creates a topic where it is subscribed for messages and then publishes all the messages to the 
 subscribers (actual users) on their own topic for the group. Thus, every user have access to the messages
 in their subscribed groups as long as the server keeps running.
 
## GETTING STARTED

### If you use intellij or eclipse
Clone the project with your IDE and add the dependencies to the modules, and just run each main class.

### Otherwise...
Add the .jar defined by the dependencies, to the classpath, compile with `javac` and run with `java`.


 
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


