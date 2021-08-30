package kitchenpos.common.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {
	@Column(name = "name")
	private String value;

	protected DisplayedName() {

	}

	public DisplayedName(String value, PurgomalumClient purgomalumClient) {
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
		}

		if (purgomalumClient.containsProfanity(value)) {
			throw new IllegalArgumentException("표시된 이름에는 비속어를 포함하지 않아야 합니다.");
		}

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DisplayedName that = (DisplayedName)o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
