package kitchenpos.products.tobe;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice {

	private BigDecimal value;

	protected ProductPrice() {
	}

	private ProductPrice(BigDecimal value) {
		if (Objects.isNull(value) || value.intValue() < 0) {
			throw new IllegalArgumentException("상품의 가격은 0원 이상이어야 합니다.");
		}
		this.value = value;
	}

	public ProductPrice(int value) {
		this(BigDecimal.valueOf(value));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductPrice that = (ProductPrice) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
