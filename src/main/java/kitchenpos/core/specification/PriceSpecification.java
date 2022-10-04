package kitchenpos.core.specification;

import java.math.BigDecimal;
import java.util.Objects;

import kitchenpos.core.constant.ExceptionMessages;

@FunctionalInterface
public interface PriceSpecification extends Specification<BigDecimal> {

	@Override
	default boolean isSatisfiedBy(BigDecimal price) {
		if (Objects.isNull(price)) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.EMPTY_PRICE_TEMPLATE, domain()));
		}

		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.NEGATIVE_PRICE_TEMPLATE, domain()));
		}

		return true;
	}

	String domain();
}
