package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

  @Test
  @DisplayName("상품은 식별자와 이름, 가격을 가진다.")
  void create() {
    Product product = new Product(
        UUID.randomUUID(),
        DisplayedName.from("후라이드치킨", new FakeDisplayedNameValidator()),
        Price.from(16_000L)
    );

    assertThat(product.getId()).isNotNull();
    assertThat(product.getName()).isEqualTo(DisplayedName.from("후라이드치킨"));
    assertThat(product.getPrice()).isEqualTo(Price.from(16_000L));
  }

  @Test
  @DisplayName("상품의 가격을 변경할 수 있다.")
  void changePrice() {
    Product product = new Product(
        UUID.randomUUID(),
        DisplayedName.from("후라이드치킨", new FakeDisplayedNameValidator()),
        Price.from(16_000L)
    );

    product.changePrice(Price.from(17_000L));
    assertThat(product.getPrice()).isEqualTo(Price.from(17_000L));
  }

}