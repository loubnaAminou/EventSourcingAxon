package ma.enset.cqrseventsourcing.queries.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.cqrseventsourcing.commonapi.enums.OperationType;
import ma.enset.cqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.enset.cqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.enset.cqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.enset.cqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.enset.cqrseventsourcing.commonapi.queries.GetAccountByIdQuery;
import ma.enset.cqrseventsourcing.commonapi.queries.GetAllAccountsQuery;
import ma.enset.cqrseventsourcing.queries.entities.Account;
import ma.enset.cqrseventsourcing.queries.entities.Operation;
import ma.enset.cqrseventsourcing.queries.repositories.AccountRepository;
import ma.enset.cqrseventsourcing.queries.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent createdEvent){
        log.info("*************************************");
        log.info("AccountCreatedEvent received");
        Account account = new Account();
        account.setId(createdEvent.getId());
        account.setSold(createdEvent.getInitialBalance());
        account.setCurrency(createdEvent.getCurrency());
        account.setStatus(createdEvent.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent activatedEvent){
        log.info("*************************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(activatedEvent.getId()).get();
        account.setStatus(activatedEvent.getStatus());
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountDebitedEvent debitedEvent){
        log.info("*************************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(debitedEvent.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(debitedEvent.getAmount());
        operation.setDate(new Date()); // WARNING : à faire dans partie écriture
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setSold(account.getSold()-debitedEvent.getAmount());
        accountRepository.save(account);
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountCreditedEvent creditedEvent){
        log.info("*************************************");
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(creditedEvent.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(creditedEvent.getAmount());
        operation.setDate(new Date()); // WARNING : à faire dans partie écriture
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setSold(account.getSold()+creditedEvent.getAmount());
        accountRepository.save(account);
        accountRepository.save(account);
    }


    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }

}
