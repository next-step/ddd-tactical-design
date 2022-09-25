package kitchenpos.core.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.core.specification.NameSpecification;

@Embeddable
public class Name {

	@Column(name = "name", nullable = false)
	private String value;

	protected Name() {
	}

	public Name(String value, NameSpecification nameSpecification) {
		if (nameSpecification.isSatisfiedBy(value)) {
			this.value = value;
		}
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
		Name that = (Name)o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "Name{" +
			"name='" + value + '\'' +
			'}';
	}
}
