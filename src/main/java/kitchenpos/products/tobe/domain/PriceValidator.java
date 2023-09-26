package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.Objects;

public class PriceValidator {

    private final BigDecimal price;

    public PriceValidator(BigDecimal price) {
        this.validatePrice(price);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void validatePrice(final BigDecimal productPrice) {
        if (Objects.isNull(productPrice)) {
            throw new IllegalArgumentException("상풍 가격은 필수로 존재해야 합니다.");
        }
        if (productPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상이어야 합니다.");
        }
    }
}
