package kitchenpos.common.exception;

import java.util.HashMap;
import java.util.Map;

public class KitchenPosException extends RuntimeException implements KitchenPosThrowable {

    private final String prefix;
    private final KitchenPosThrowable kitchenPosThrowable;
    private Map<String, Object> relatedValues;

    public KitchenPosException(String prefix, KitchenPosThrowable kitchenPosThrowable) {
        this.prefix = prefix;
        this.kitchenPosThrowable = kitchenPosThrowable;
        relatedValues = new HashMap<>();
    }

    @Override
    public String getMessage() {
        return this.prefix + " " + this.kitchenPosThrowable.getMessage() + " " + this.relatedValues;
    }

}
