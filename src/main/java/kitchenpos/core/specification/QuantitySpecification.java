package kitchenpos.core.specification;

import java.util.Objects;

import kitchenpos.core.constant.ExceptionMessages;

@FunctionalInterface
public interface QuantitySpecification extends Specification<Long> {

	default boolean isSatisfiedBy(Long quantity) {
		if (Objects.isNull(quantity)) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.EMPTY_QUANTITY_TEMPLATE, domain()));
		}

		if (quantity.compareTo(0L) < 0) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.NEGATIVE_QUANTITY_TEMPLATE, domain()));
		}

		return true;
	}

	String domain();

}
