package kitchenpos.products.domain.tobe.policy;

import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;

import java.math.BigDecimal;

public class FakeSuccessProductPricingRule implements ProductPricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        return true;
    }
}
