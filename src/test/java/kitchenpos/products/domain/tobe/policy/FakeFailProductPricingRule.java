package kitchenpos.products.domain.tobe.policy;

import kitchenpos.products.domain.tobe.domain.policy.ProductPricingRule;
import kitchenpos.products.exception.ProductPricingRuleViolationException;

import java.math.BigDecimal;

public class FakeFailProductPricingRule implements ProductPricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        throw new ProductPricingRuleViolationException("잘못된 상품 명입니다");
    }
}
