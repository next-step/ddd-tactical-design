package kitchenpos.eatinorders.tobe.tableGroup.domain.exception;

public class TableAlreadyGroupedException extends RuntimeException {

    public TableAlreadyGroupedException(final String message) {
        super(message);
    }
}
