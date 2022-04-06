package kitchenpos.products.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

	@Test
	@DisplayName("상품의 가격이 0원 이상이면 등록할 수 있다")
	void constructor01() {
		assertThatCode(() -> new Product(UUID.randomUUID(), "김치", 0)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("상품의 가격이 0원 미만이면 등록할 수 없다")
	void constructor02() {
		assertThatThrownBy(() -> new Product(UUID.randomUUID(), "김치", -1)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("상품의 가격이 0원 이상이면 변경할 수 있다")
	void price01() {
		Product product = new Product(UUID.randomUUID(), "김치", 0);

		product.changePrice(10);

		assertThat(product.getPrice()).isEqualTo(new ProductPrice(10));
	}

	@Test
	@DisplayName("상품의 가격이 0원 미만일 때는 변경할 수 없다")
	void price02() {
		Product product = new Product(UUID.randomUUID(), "김치", 0);

		assertThatThrownBy(() -> product.changePrice(-1)).isInstanceOf(IllegalArgumentException.class);
	}
}
