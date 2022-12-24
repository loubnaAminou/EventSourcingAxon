package ma.enset.cqrseventsourcing.commonapi.events;

import lombok.Getter;
import ma.enset.cqrseventsourcing.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String> {
    @Getter private AccountStatus status;

    public AccountActivatedEvent(String id) {
        super(id);
    }

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
