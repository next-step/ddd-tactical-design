package kitchenpos.products.tobe.domain;

import java.util.Objects;

public class ProductName {
	private final String name;

	public ProductName(String name, ForbiddenWordCheckPolicy forbiddenWordCheckPolicy) {
		verifyName(name, forbiddenWordCheckPolicy);
		this.name = name;
	}

	private void verifyName(String name, ForbiddenWordCheckPolicy forbiddenWordCheckPolicy) {
		if (Objects.isNull(name) || name.isEmpty()) {
			throw new IllegalArgumentException("상품 이름은 비워둘 수 없습니다. name: " + name);
		}

		if (forbiddenWordCheckPolicy.hasForbiddenWord(name)) {
			throw new IllegalArgumentException("상품 이름에 금칙어가 포함될 수 없습니다. name: " + name);
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ProductName that = (ProductName) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
