package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("상품")
class ProductTest {

  private DisplayedName name;
  private ProductPrice price;

  @BeforeEach
  void setup() {
    name = new DisplayedName("후라이드");
    price = new ProductPrice(20_000);
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

    ProductPrice changePrice = new ProductPrice(25_000);
    product.changePrice(changePrice);

    assertThat(product.getPrice()).isEqualTo(changePrice);
  }
}
