package kitchenpos.product.domain;

import java.util.Objects;

import org.apache.logging.log4j.util.Strings;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.common.domain.PurgomalumClient;

@Embeddable
public class ProductName {
	public static final String NULL_OR_EMPTY_NAME_ERROR = "상품 이름은 비워 둘 수 없습니다.";
	public static final String NAME_WITH_PROFANITY_ERROR = "상품 이름에 비속어가 포함될 수 없습니다.";

	@Column(name = "name", nullable = false)
	private String value;

	protected ProductName() {
	}

	public ProductName(String value, PurgomalumClient purgomalumClient) {
		if (Strings.isEmpty(value)) {
			throw new IllegalArgumentException(NULL_OR_EMPTY_NAME_ERROR);
		}

		if (purgomalumClient.containsProfanity(value)) {
			throw new IllegalArgumentException(NAME_WITH_PROFANITY_ERROR);
		}

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		ProductName that = (ProductName)object;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
