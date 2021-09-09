package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.common.Value;

@Embeddable
public class DisplayedName extends Value<DisplayedName> {
	@Column(name = "name")
	private String value;

	protected DisplayedName() {

	}

	public DisplayedName(final String value, final Profanities profanities) {
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
		}

		if (profanities.contains(value)) {
			throw new IllegalArgumentException("이름에는 욕설이 포함될 수 없습니다.");
		}

		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
