package kitchenpos.products.domain.tobe.policy;

import kitchenpos.products.domain.tobe.domain.policy.ProductNamingRule;
import kitchenpos.products.exception.ProductNamingRuleViolationException;

public class FakeFailProductNamingRule implements ProductNamingRule {
    @Override
    public boolean checkRule(String name) {
        throw new ProductNamingRuleViolationException("잘못된 상품 명입니다");
    }
}
