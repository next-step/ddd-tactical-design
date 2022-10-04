package kitchenpos.core;

import java.math.BigDecimal;

import kitchenpos.core.domain.Name;
import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;
import kitchenpos.core.specification.NameSpecification;
import kitchenpos.core.specification.PriceSpecification;
import kitchenpos.core.specification.QuantitySpecification;

public final class CoreFixtures {

	private CoreFixtures() {
	}

	public static final Name name(String value, NameSpecification nameSpecification) {
		return new Name(value, nameSpecification);
	}

	public static Price price(long value, PriceSpecification priceSpecification) {
		return new Price(BigDecimal.valueOf(value), priceSpecification);
	}

	public static Quantity quantity(long value, QuantitySpecification quantitySpecification) {
		return new Quantity(value, quantitySpecification);
	}
}
