package kitchenpos.support.policy;

import kitchenpos.support.exception.PricingRuleViolationException;

import java.math.BigDecimal;

public class FakeFailPricingRule implements PricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        throw new PricingRuleViolationException();
    }
}
