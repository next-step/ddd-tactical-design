package kitchenpos.common.policy;

import kitchenpos.common.exception.NamingRuleViolationException;

public class FakeFailNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        throw new NamingRuleViolationException();
    }
}
