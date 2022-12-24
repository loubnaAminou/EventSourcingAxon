package ma.enset.cqrseventsourcing.commands.aggregates;

import ma.enset.cqrseventsourcing.commonapi.commands.CreateAccountCommand;
import ma.enset.cqrseventsourcing.commonapi.commands.CreditAccountCommand;
import ma.enset.cqrseventsourcing.commonapi.commands.DebitAccountCommand;
import ma.enset.cqrseventsourcing.commonapi.enums.AccountStatus;
import ma.enset.cqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.enset.cqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.enset.cqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.enset.cqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.enset.cqrseventsourcing.commonapi.exceptions.BalanceNotSufficientException;
import ma.enset.cqrseventsourcing.commonapi.exceptions.NegatifAmountException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    // focntion de décision
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if(command.getInitialBalance() < 0) throw new RuntimeException("Impossible de créer un compte avec un solde négatif");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED

        ));

    }

    // Fonction d'évolution
    @EventSourcingHandler
    public void on(AccountCreatedEvent createdEvent){
        this.accountId = createdEvent.getId();
        this.balance = createdEvent.getInitialBalance();
        this.currency = createdEvent.getCurrency();
        this.status = createdEvent.getStatus();

        AggregateLifecycle.apply(new AccountActivatedEvent(
                createdEvent.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.status = accountActivatedEvent.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount() < 0) throw new NegatifAmountException("Amount should NOT be negatif !");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent creditedEvent){
        this.balance+= creditedEvent.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(command.getAmount() < 0) throw new NegatifAmountException("Amount should NOT be negatif !");
        if(this.balance < command.getAmount()) throw new BalanceNotSufficientException("NO MONEY To debit !");

        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent creditedEvent){
        this.balance-= creditedEvent.getAmount();
    }


}
