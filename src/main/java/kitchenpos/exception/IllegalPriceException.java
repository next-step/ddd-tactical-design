package kitchenpos.exception;

import java.math.BigDecimal;

public class IllegalPriceException extends IllegalArgumentException {
    public IllegalPriceException(BigDecimal price) {
        super("잘못된 가격입니다. : " + price);
    }

    public IllegalPriceException(String s) {
        super(s);
    }
}
