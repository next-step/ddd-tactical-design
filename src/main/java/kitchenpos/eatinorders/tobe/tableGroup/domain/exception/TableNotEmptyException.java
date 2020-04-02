package kitchenpos.eatinorders.tobe.tableGroup.domain.exception;

public class TableNotEmptyException extends RuntimeException {

    public TableNotEmptyException(final String message) {
        super(message);
    }
}
