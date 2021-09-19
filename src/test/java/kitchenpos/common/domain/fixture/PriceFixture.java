package kitchenpos.common.domain.fixture;

import java.math.BigDecimal;

import kitchenpos.common.domain.Price;

public class PriceFixture {
	public static Price 가격(long value) {
		return new Price(new BigDecimal(value));
	}
}
