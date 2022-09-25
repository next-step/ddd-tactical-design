package kitchenpos.core.specification;

import java.math.BigDecimal;

@FunctionalInterface
public interface PriceSpecification extends Specification<BigDecimal> {

	boolean isSatisfiedBy(BigDecimal price);
}
