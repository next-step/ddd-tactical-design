package kitchenpos.products.domain.tobe.domain.policy;

import java.math.BigDecimal;

public interface ProductPricingRule {
    void checkRule(BigDecimal price);
}
