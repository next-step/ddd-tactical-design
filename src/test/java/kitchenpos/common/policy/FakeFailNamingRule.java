package kitchenpos.common.policy;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.products.exception.ProductNamingRuleViolationException;

public class FakeFailNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        throw new ProductNamingRuleViolationException("잘못된 상품 명입니다");
    }
}
