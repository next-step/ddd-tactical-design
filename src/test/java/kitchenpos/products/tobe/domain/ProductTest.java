package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeForbiddenWordCheckPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

	@DisplayName("상품을 생성할 수 있다")
	@Test
	void createProduct() {
		// given
		UUID id = UUID.randomUUID();
		ProductName productName = new ProductName("라면", new FakeForbiddenWordCheckPolicy());
		ProductPrice productPrice = new ProductPrice(BigDecimal.ONE);

		// when
		Product result = new Product(id, productName, productPrice);

		// then
		assertThat(result).isNotNull();
	}

	@DisplayName("상품의 가격을 변경할 수 있다")
	@Test
	void changePrice() {
		// given
		ProductPrice oldPrice = new ProductPrice(BigDecimal.ONE);
		ProductPrice newPrice = new ProductPrice(BigDecimal.TEN);

		Product product = createProduct(oldPrice);

		// when
		product.changePrice(newPrice);

		// then
		assertThat(product.getPrice()).isEqualTo(newPrice);
	}

	private Product createProduct(ProductPrice oldPrice) {
		UUID id = UUID.randomUUID();
		ProductName productName = new ProductName("라면", new FakeForbiddenWordCheckPolicy());

		return new Product(id, productName, oldPrice);
	}
}
