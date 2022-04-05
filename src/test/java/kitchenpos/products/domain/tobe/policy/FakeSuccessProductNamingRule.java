package kitchenpos.products.domain.tobe.policy;

import kitchenpos.common.policy.NamingRule;

public class FakeSuccessProductNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        return true;
    }
}
