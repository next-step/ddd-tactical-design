package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.common.exception.PricingRuleViolationException;
import kitchenpos.common.policy.PricingRule;

import java.math.BigDecimal;
import java.util.Objects;

public class DefaultProductPricingRule implements PricingRule {
    @Override
    public boolean checkRule(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new PricingRuleViolationException("잘못된 상품 금액입니다");
        }
        return true;
    }
}
