package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.specification.PriceSpecification;

public class ProductPriceSpecification implements PriceSpecification {

	@Override
	public boolean isSatisfiedBy(BigDecimal price) {
		if (Objects.isNull(price)) {
			throw new IllegalArgumentException(ExceptionMessages.Product.PRICE_IS_EMPTY);
		}

		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException(ExceptionMessages.Product.PRICE_IS_NEGATIVE);
		}

		return true;
	}
}
