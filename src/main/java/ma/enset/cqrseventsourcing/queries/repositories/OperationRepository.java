package ma.enset.cqrseventsourcing.queries.repositories;

import ma.enset.cqrseventsourcing.queries.entities.Account;
import ma.enset.cqrseventsourcing.queries.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
