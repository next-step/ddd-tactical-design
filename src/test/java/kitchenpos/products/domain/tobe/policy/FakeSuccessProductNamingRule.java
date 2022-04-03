package kitchenpos.products.domain.tobe.policy;

import kitchenpos.products.domain.tobe.domain.policy.ProductNamingRule;

public class FakeSuccessProductNamingRule implements ProductNamingRule {
    @Override
    public boolean checkRule(String name) {
        return true;
    }
}
