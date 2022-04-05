package kitchenpos.common.policy;

import kitchenpos.common.exception.PricingRuleViolationException;

import java.math.BigDecimal;

public class FakeFailPricingRule implements PricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        throw new PricingRuleViolationException();
    }
}
