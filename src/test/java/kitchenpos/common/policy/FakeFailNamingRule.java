package kitchenpos.common.policy;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.products.exception.ProductNamingRuleViolationException;

public class FakeFailNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        throw new NamingRuleViolationException();
    }
}
