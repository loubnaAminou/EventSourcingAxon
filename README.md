# Event Driven Architecture CQRS and Event Sourcing

### Project Structure
Using this design pattern, it helps to separate the Write side (commands) and Read side (queries), and a common components between the two sides.

![structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/structure.png)

## Commands : Writing side 

![commands-structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/commands-structure.png)

in this part, we create an aggregate in which we manage the business layer. By managing that, we create commands, and we change the status of the aggregate by applying new events.

![aggregate](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/aggregate.png)

Then, a controller is created to define the HTTP methods in order to use the CommandGateway which is responsible for sending commands. So by that, we can apply the POST method successfully.

![output](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/commands-output.png)

also, we can read the content of the event store through a GET mapping for each account using its ID in the URL

![eventstore](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/eventstore.png)


## Queries : Reading side 
as usual, we create entities, repositories, services and controllers to manage the data of the project

![queries-structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/queries-structure.png)

at this level, the service is not responsible for the rules anymore, we are only receiving the events and trying to read the content and save it into the repositories.

![service](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/service.png)

as a result, we can check for each account to show its details and the different operations we had for this account such as DEBIT or CREDIT.

![account](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/account.png)

For the Database, two tables are created as repositories "Account" and "Operation, including to the tables those are created by default by the AXON framework.

![database](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/database.png)





