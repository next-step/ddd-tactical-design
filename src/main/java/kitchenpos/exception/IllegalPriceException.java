package kitchenpos.exception;

import java.math.BigDecimal;

public class IllegalPriceException extends IllegalArgumentException {
    public IllegalPriceException(String s) {
        super(s);
    }
}
