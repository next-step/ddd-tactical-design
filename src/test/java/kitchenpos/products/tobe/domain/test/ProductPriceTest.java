package kitchenpos.products.tobe.domain.test;

import kitchenpos.products.tobe.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static kitchenpos.products.tobe.domain.fixture.PriceFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ProductPriceTest {
  private static Stream<Arguments> provideBigDecimalForIsNotNullAndMinusValue() {
    return Stream.of(
      Arguments.of((BigDecimal) null),
      Arguments.of(MINUS_PRICE)
    );
  }

  @Test
  @DisplayName("상품의 가격을 등록할 수 있다.")
  void test1() {
    assertDoesNotThrow(
      () -> new ProductPrice(PRICE)
    );
  }

  @ParameterizedTest
  @DisplayName("상품의 가격이 비어있거나 음수이면 IllegalArgumentException 예외 발생")
  @MethodSource("provideBigDecimalForIsNotNullAndMinusValue")
  void test2(BigDecimal price) {
    assertThatThrownBy(
      () -> new ProductPrice(price)
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("동등성 비교")
  void test3() {
    ProductPrice price = new ProductPrice(PRICE);
    assertAll(
      () -> assertThat(price).isEqualTo(new ProductPrice(PRICE)),
      () -> assertThat(price).isNotEqualTo(new ProductPrice(CHANGE_PRICE))
    );
  }
}
