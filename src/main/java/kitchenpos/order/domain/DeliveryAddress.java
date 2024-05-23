package kitchenpos.order.domain;

import java.util.Objects;

import org.springframework.util.StringUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {
	public static final String NULL_OR_EMPTY_ADDRESS_ERROR = "배송 주소는 비워 둘 수 없습니다.";

	@Column(name = "delivery_address", nullable = false)
	private String value;

	protected DeliveryAddress() {
	}

	public DeliveryAddress(String value) {
		if (!StringUtils.hasText(value)) {
			throw new IllegalArgumentException(NULL_OR_EMPTY_ADDRESS_ERROR);
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
		DeliveryAddress that = (DeliveryAddress)object;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
