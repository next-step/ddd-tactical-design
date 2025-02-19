package kitchenpos.product.domain.service;

import java.math.BigDecimal;

public interface ProductCreatePolicy {
    BigDecimal validatePrice(BigDecimal price);

    String validateName(String productName, ProductPurgomalumClient purgomalumClient);
}
