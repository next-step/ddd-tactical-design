package kitchenpos.products.domain.tobe.domain.policy;

import kitchenpos.products.exception.ProductPricingRuleViolationException;

import java.math.BigDecimal;
import java.util.Objects;

public class DefaultProductPricingRule implements ProductPricingRule {
    @Override
    public void checkRule(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductPricingRuleViolationException("잘못된 상품 금액입니다");
        }
    }
}
