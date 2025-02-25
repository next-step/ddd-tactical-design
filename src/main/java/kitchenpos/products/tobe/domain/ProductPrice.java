package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;
import kitchenpos.shared.domain.Money;

public record ProductPrice(Money price) {
    public ProductPrice {
        if (price == null || price.isLessThan(Money.ZERO())) {
            throw new InvalidProductPriceException("상품 가격은 null 이거나 0보다 작을 수 없습니다.");
        }
    }

    public ProductPrice (long price) {
        this(Money.of(price));
    }
}
