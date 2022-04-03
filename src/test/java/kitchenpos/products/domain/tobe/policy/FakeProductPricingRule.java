package kitchenpos.products.domain.tobe.policy;

import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;

import java.math.BigDecimal;

public class FakeProductPricingRule implements ProductPricingRule {

    @Override
    public void checkRule(BigDecimal price) {

    }
}
