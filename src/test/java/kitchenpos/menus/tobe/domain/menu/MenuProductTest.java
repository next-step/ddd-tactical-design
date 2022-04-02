package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

  @DisplayName("메뉴 상품의 금액 (가격 * 수량)을 계산할 수 있다.")
  @Test
  void calculateAmountTest() {
    //given
    String productName = "테스트상품";
    BigDecimal productPrice = BigDecimal.valueOf(1000L);
    Long productId = 1L;
    MenuProductQuantity quantity = new MenuProductQuantity(3L);
    MenuProduct givenMenuProduct = new MenuProduct(productName, productPrice, productId, quantity);

    //when
    BigDecimal result = givenMenuProduct.calculateAmount();

    //then
    assertThat(result).isEqualTo(productPrice.multiply(BigDecimal.valueOf(quantity.getValue())));
  }

}
