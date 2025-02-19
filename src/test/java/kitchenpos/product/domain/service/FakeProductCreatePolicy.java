package kitchenpos.product.domain.service;

import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.product.domain.model.ProductNameValidator;

public class FakeProductCreatePolicy implements ProductCreatePolicy {

    @Override
    public BigDecimal validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
        }
        return price;
    }

    public String validateName(String productName, ProductPurgomalumClient purgomalumClient) {
        return new ProductNameValidator(productName, purgomalumClient).name();
    }
}
