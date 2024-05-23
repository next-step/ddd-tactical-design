package kitchenpos.common.domain;

import java.util.Objects;

import org.springframework.util.StringUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class NonEmptyName {
	public static final String NULL_OR_EMPTY_NAME_ERROR = "이름은 비워 둘 수 없습니다.";

	@Column(name = "name", nullable = false)
	private String value;

	protected NonEmptyName() {
	}

	public NonEmptyName(String value) {
		if (!StringUtils.hasText(value)) {
			throw new IllegalArgumentException(NULL_OR_EMPTY_NAME_ERROR);
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
		NonEmptyName that = (NonEmptyName)object;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
