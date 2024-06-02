package kitchenpos.exception;

import java.math.BigDecimal;

public class IllegalPriceException extends IllegalArgumentException {
    public IllegalPriceException(String descriptions, String p) {
        super(descriptions+ " " + p);
    }

    public IllegalPriceException(String descriptions, BigDecimal price) {
        super(descriptions + " " + price);
    }

    public IllegalPriceException(String descriptions, Long price) {
        super(descriptions + " " + price);
    }
}
