package kitchenpos.products.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class Product {
	private final UUID id;
	private final ProductName productName;
	private ProductPrice productPrice;

	public Product(UUID id, ProductName productName, ProductPrice productPrice) {
		this.id = id;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public void changePrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

	public ProductPrice getPrice() {
		return productPrice;
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
