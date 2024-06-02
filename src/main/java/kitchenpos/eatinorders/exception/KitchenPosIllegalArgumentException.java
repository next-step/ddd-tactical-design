package kitchenpos.eatinorders.exception;

public class KitchenPosIllegalArgumentException extends IllegalArgumentException {
    public KitchenPosIllegalArgumentException(KitchenPosExceptionMessage message, Object...parameters) {
        super(String.format(message.message(), parameters));
    }
}
