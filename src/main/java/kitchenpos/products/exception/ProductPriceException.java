package kitchenpos.products.exception;

import java.math.BigDecimal;

public class ProductPriceException extends RuntimeException {

    private static final String MESSAGE = "제품의 가격(%s)은 0이상 이여야 합니다.";
    public ProductPriceException(BigDecimal price) {
        super(String.format(MESSAGE, price));
    }
}
