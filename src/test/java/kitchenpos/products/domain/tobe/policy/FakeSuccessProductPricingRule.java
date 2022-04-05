package kitchenpos.products.domain.tobe.policy;

import kitchenpos.common.policy.PricingRule;

import java.math.BigDecimal;

public class FakeSuccessProductPricingRule implements PricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        return true;
    }
}
