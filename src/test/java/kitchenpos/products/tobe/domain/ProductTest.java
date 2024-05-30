package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import kitchenpos.products.tobe.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
  private ProductRepository productRepository;

  @BeforeEach
  public void init() {
    this.productRepository = new InMemoryProductRepository();
  }

  @DisplayName("상품을 등록할 수 있다.")
  @Test
  void create() {
    Product expected = ProductFixture.normal();
    Product actual = productRepository.save(expected);
    assertAll(
        () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
        () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
    );
  }

  @DisplayName("상품의 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    ProductPrice expected = ProductFixture.createProductPrice(new BigDecimal(10_000L));
    Product actual = ProductFixture.normal();
    actual.changePrice(expected);
    assertThat(actual.getPrice()).isEqualTo(expected);
  }
}
