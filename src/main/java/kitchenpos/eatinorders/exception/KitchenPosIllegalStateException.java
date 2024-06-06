package kitchenpos.eatinorders.exception;

public class KitchenPosIllegalStateException extends IllegalStateException {
    public KitchenPosIllegalStateException(KitchenPosExceptionMessage message, Object...parameters) {
        super(String.format(message.message(), parameters));
    }
}
