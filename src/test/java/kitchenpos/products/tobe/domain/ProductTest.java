package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

  @DisplayName("가격을 변경할 수 있다.")
  @Test
  void changePriceTest() {
    //given
    Product givenProduct = new Product(new DisplayedName("이름", false),BigDecimal.valueOf(1000));
    Price toChangePrice = new Price(BigDecimal.valueOf(2000));

    //when
    givenProduct.changePrice(toChangePrice);

    //then
    assertThat(givenProduct.getPrice()).isEqualTo(toChangePrice);
  }

}
