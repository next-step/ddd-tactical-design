package kitchenpos.common.policy;

import kitchenpos.common.policy.PricingRule;

import java.math.BigDecimal;

public class FakeSuccessPricingRule implements PricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        return true;
    }
}
