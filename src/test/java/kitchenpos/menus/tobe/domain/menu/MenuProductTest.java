package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;
import kitchenpos.supports.domain.tobe.PriceFixture;
import kitchenpos.supports.domain.tobe.Quantity;
import kitchenpos.supports.domain.tobe.QuantityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {
  @DisplayName("메뉴상품을 등록할 수 있다.")
  @Test
  void create() {
    Long seq = 1L;
    Quantity quantity = QuantityFixture.normal();
    MenuProduct menuProduct = MenuFixture.createMenuProduct(seq, quantity);
    assertAll(
        () -> assertThat(menuProduct.getSeq()).isEqualTo(seq),
        () -> assertThat(menuProduct.getQuantity()).isEqualTo(quantity)
    );
  }

  @DisplayName("메뉴상품의 가격은 상품의 가격과 수량의 곱이다.")
  @Test
  void getPrice() {
    RegisteredProduct registeredProduct = MenuFixture.createRegisteredProduct(UUID.randomUUID(),
        PriceFixture.create(new BigDecimal(5_000L)));
    Long seq = 1L;
    Quantity quantity = QuantityFixture.create(3L);
    Price expected = registeredProduct.getPrice().multiply(quantity);
    MenuProduct menuProduct = MenuFixture.createMenuProduct(seq, quantity, registeredProduct);
    assertThat(menuProduct.getPrice()).isEqualTo(expected);
  }
}
