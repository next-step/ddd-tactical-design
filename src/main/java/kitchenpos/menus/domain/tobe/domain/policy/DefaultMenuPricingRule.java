package kitchenpos.menus.domain.tobe.domain.policy;

import kitchenpos.support.exception.PricingRuleViolationException;
import kitchenpos.support.policy.PricingRule;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class DefaultMenuPricingRule implements PricingRule {
    private final List<TobeMenuProduct> menuProducts;

    public DefaultMenuPricingRule(List<TobeMenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new PricingRuleViolationException();
        }
        this.menuProducts = menuProducts;
    }

    @Override
    public boolean checkRule(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(getTotalAmount()) > 0) {
            throw new PricingRuleViolationException();
        }
        return true;
    }

    private BigDecimal getTotalAmount() {
        return menuProducts.stream()
                .map(TobeMenuProduct::calculateAmount)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
}
