package kitchenpos.products.domain.tobe.policy;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.products.exception.ProductNamingRuleViolationException;

public class FakeFailProductNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        throw new ProductNamingRuleViolationException("잘못된 상품 명입니다");
    }
}
