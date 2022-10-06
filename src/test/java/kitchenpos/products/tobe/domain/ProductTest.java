package kitchenpos.products.tobe.domain;

import kitchenpos.global.vo.Price;
import kitchenpos.products.tobe.vo.ProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품")
class ProductTest {

  private ProductName name;
  private Price price;

  @BeforeEach
  void setup() {
    name = new ProductName("후라이드");
    price = new Price(20_000);
  }

  @DisplayName("상품 생성")
  @Test
  void createProduct() {
    Product product = new Product(name, price);

    assertThat(product.getName()).isEqualTo(name);
    assertThat(product.getPrice()).isEqualTo(price);
  }

  @DisplayName("상품 가격을 변경할 수 있다.")
  @Test
  void changeProductPrice() {
    Product product = new Product(name, price);

    Price changePrice = new Price(25_000);
    product.changePrice(changePrice);

    assertThat(product.getPrice()).isEqualTo(changePrice);
  }
}
