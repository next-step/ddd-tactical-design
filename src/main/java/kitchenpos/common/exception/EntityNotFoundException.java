package kitchenpos.common.exception;


public abstract class EntityNotFoundException extends KitchenPosException {
    protected EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
