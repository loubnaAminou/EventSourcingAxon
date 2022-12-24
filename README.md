# Event Driven Architecture CQRS and Event Sourcing

### Project Structure
Using this design pattern, it helps to separate the Write side (commands) and Read side (queries), and a common components between the two sides.

![structure](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/structure.png)

## Commands : Writing side 
in this part, we create an aggregate in which we manage the business layer. By managing that, we create commands, and we change the status of the aggregate by applying new events.

![aggregate](https://github.com/loubnaAminou/EventSourcingAxon/blob/main/screenshots/aggregate.png)



