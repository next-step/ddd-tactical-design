package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;
import kitchenpos.supports.domain.tobe.PriceFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisteredProductTest {
  @DisplayName("등록된 상품을 생성할 수 있다.")
  @Test
  void create() {
    UUID id = UUID.randomUUID();
    Price price = PriceFixture.normal();
    RegisteredProduct registeredProduct = MenuFixture.createRegisteredProduct(id, price);
    assertAll(
        () -> assertThat(registeredProduct.getId()).isEqualTo(id),
        () -> assertThat(registeredProduct.getPrice()).isEqualTo(price)
    );
  }
}
