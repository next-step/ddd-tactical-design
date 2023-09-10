package kitchenpos.common.exception;

public class KitchenPosException extends RuntimeException implements KitchenPosThrowable {

    private final String prefix;
    private final KitchenPosThrowable kitchenPosThrowable;

    public KitchenPosException(String prefix, KitchenPosThrowable kitchenPosThrowable) {
        this.prefix = prefix;
        this.kitchenPosThrowable = kitchenPosThrowable;
    }

    @Override
    public String getMessage() {
        return this.prefix + " " + this.kitchenPosThrowable.getMessage();
    }

}
