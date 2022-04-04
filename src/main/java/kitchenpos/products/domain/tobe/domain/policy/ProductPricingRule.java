package kitchenpos.products.domain.tobe.domain.policy;

import java.math.BigDecimal;

public interface ProductPricingRule {
    boolean checkRule(BigDecimal price);
}
