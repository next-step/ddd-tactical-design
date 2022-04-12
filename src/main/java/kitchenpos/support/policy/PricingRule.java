package kitchenpos.support.policy;

import java.math.BigDecimal;

public interface PricingRule {
    boolean checkRule(BigDecimal price);
}
