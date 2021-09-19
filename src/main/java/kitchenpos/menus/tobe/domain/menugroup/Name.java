package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.common.Value;

@Embeddable
public class Name extends Value<Name> {
	@Column(name = "name")
	private String value;

	protected Name() {

	}

	public Name(String value) {
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
		}

		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
