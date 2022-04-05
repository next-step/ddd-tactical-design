package kitchenpos.common.policy;

import kitchenpos.common.policy.NamingRule;

public class FakeSuccessNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        return true;
    }
}
