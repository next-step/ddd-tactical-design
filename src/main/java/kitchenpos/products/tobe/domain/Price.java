package kitchenpos.products.tobe.domain;

import kitchenpos.commons.ValueObject;

import java.math.BigDecimal;

public class Price extends ValueObject<Price> {
    private BigDecimal value;

    public Price(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품의 가격은 0원 이상이어야 한다.");
        }
        this.value = value;
    }
}
