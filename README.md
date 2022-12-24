# Event Driven Architecture CQRS and Event Sourcing

### Project Structure
Using this design pattern, it helps to separate the Write side (commands) and Read side (queries), and a common components between the two sides.

![structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/structure.png)

## Commands : Writing side 

![commands-structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/commands-structure.png)

in this part, we create an aggregate in which we manage the business layer. By managing that, we create commands, and we change the status of the aggregate by applying new events.

![aggregate](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/aggregate.png)

Then, a controller is created to define the HTTP methods in order to use the CommandGateway which is responsible for sending commands. So by that, we can apply th ePOST method successfully.

![output](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/commands-output.png)

## Queries : Reading side 
as usual, we create entities, repositories, services and controllers to manage the data of the project

![queries-structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/queries-structure.png)





