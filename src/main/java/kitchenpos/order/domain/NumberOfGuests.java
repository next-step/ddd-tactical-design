package kitchenpos.order.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

	public static final String INVALID_VALUE_ERROR = "손님의 수는 0보다 작을 수 없습니다.";

	@Column(name = "number_of_guests", nullable = false)
	private int value;

	protected NumberOfGuests() {
	}

	protected NumberOfGuests(int value) {
		if (value < 0) {
			throw new IllegalArgumentException(INVALID_VALUE_ERROR);
		}

		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		NumberOfGuests that = (NumberOfGuests)object;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
