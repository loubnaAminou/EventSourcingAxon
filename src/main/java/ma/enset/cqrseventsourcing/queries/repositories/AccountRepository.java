package ma.enset.cqrseventsourcing.queries.repositories;

import ma.enset.cqrseventsourcing.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
