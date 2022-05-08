package kitchenpos.products.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class Name {
	@Column(name = "name", nullable = false)
	private final String value;

	public Name(String value, PurgomalumClient purgomalums) {
		this.value = validateDisplayName(value, purgomalums);

	}

	private String validateDisplayName(String name, PurgomalumClient purgomalums) {
		if (Objects.isNull(name) || purgomalums.containsProfanity(name)) {
			throw new IllegalArgumentException();
		}

		return name;
	}

	public String getValue() {
		return value;
	}
}
