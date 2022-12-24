package ma.enset.cqrseventsourcing.commonapi.exceptions;

public class NegatifAmountException extends RuntimeException {
    public NegatifAmountException(String message) {
        super(message);
    }
}
