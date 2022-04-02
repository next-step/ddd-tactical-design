package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {
	private UUID id;
	private ProductName productName;
	private BigDecimal price;

	public Product(UUID id, ProductName productName, BigDecimal price) {
		verifyPrice(price);

		this.id = id;
		this.productName = productName;
		this.price = price;
	}

	public void changePrice(BigDecimal price) {
		verifyPrice(price);
		this.price = price;
	}

	private void verifyPrice(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("상품 가격은 0원보다 작을 수 없습니다. price: " + price);
		}
	}

	public UUID getId() {
		return id;
	}

	public ProductName getProductName() {
		return productName;
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
