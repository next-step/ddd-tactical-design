package kitchenpos.support.policy;

import kitchenpos.support.exception.NamingRuleViolationException;

public class FakeFailNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        throw new NamingRuleViolationException();
    }
}
