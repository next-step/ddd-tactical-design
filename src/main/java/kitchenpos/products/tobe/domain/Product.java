package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {
	private UUID id;
	private String name;
	private BigDecimal price;

	public Product(UUID id, String name, BigDecimal price, ForbiddenWordCheckPolicy forbiddenWordCheckPolicy) {
		verifyName(name, forbiddenWordCheckPolicy);
		verifyPrice(price);

		this.id = id;
		this.name = name;
		this.price = price;
	}

	public void changePrice(BigDecimal price) {
		verifyPrice(price);
		this.price = price;
	}

	private void verifyName(String name, ForbiddenWordCheckPolicy forbiddenWordCheckPolicy) {
		if (Objects.isNull(name) || name.isEmpty()) {
			throw new IllegalArgumentException("상품 이름은 비워둘 수 없습니다. name: " + name);
		}

		if (forbiddenWordCheckPolicy.hasForbiddenWord(name)) {
			throw new IllegalArgumentException("상품 이름에 금칙어가 포함될 수 없습니다. name: " + name);
		}
	}

	private void verifyPrice(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("상품 가격은 0원보다 작을 수 없습니다. price: " + price);
		}
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Product product = (Product) o;
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
