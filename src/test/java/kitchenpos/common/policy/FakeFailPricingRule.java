package kitchenpos.common.policy;

import kitchenpos.common.policy.PricingRule;
import kitchenpos.products.exception.ProductPricingRuleViolationException;

import java.math.BigDecimal;

public class FakeFailPricingRule implements PricingRule {

    @Override
    public boolean checkRule(BigDecimal price) {
        throw new ProductPricingRuleViolationException("잘못된 상품가격 입니다");
    }
}
