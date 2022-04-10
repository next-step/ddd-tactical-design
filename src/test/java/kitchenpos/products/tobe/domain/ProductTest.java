package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static kitchenpos.products.tobe.domain.ProductFixture.상품;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

  private static final BigDecimal PRICE = BigDecimal.valueOf(1000L);
  private static final BigDecimal CHANGE_PRICE = BigDecimal.valueOf(2000L);
  private static final BigDecimal MINUS_PRICE = BigDecimal.valueOf(-1000L);

  private static Stream<Arguments> provideBigDecimalForIsNotNullAndMinusValue() {
    return Stream.of(
      Arguments.of((BigDecimal) null),
      Arguments.of(MINUS_PRICE)
    );
  }

  @Test
  @DisplayName("상품을 등록할 수 있다.")
  void test1() {
    assertThatCode(
      () -> new Product(null, "name", PRICE)
    ).doesNotThrowAnyException();
  }

  @ParameterizedTest
  @DisplayName("상품 이름이 존재하지 않으면 IllegalArgumentException 예외 발생")
  @NullAndEmptySource
  void test2(String name) {
    assertThatThrownBy(
      () -> new Product(null, name, BigDecimal.valueOf(1000L))
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("상품 가격에 음수이면 IllegalArgumentException 예외 발생")
  void test3() {
    assertThatThrownBy(
      () -> new Product(null, "name", BigDecimal.valueOf(-1000L))
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @DisplayName("존재하지 않으면 IllegalArgumentException 예외 발생")
  @MethodSource("provideBigDecimalForIsNotNullAndMinusValue")
  void test4(BigDecimal price) {
    assertThatThrownBy(
      () -> new Product(null, "name", price)
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("상품의 가격을 변경할 수 있다.")
  void test5() {
    assertAll(
      () -> assertDoesNotThrow(() -> 상품.changePrice(CHANGE_PRICE)),
      () -> assertThat(상품.getPrice()).isEqualTo(new ProductPrice(CHANGE_PRICE))
    );
  }

  @ParameterizedTest
  @MethodSource("provideBigDecimalForIsNotNullAndMinusValue")
  @DisplayName("변경하는 상품의 가격이 음수거나 존재하지 않으면 IllegalArgumentException 예외 발생")
  void test6(BigDecimal price) {
    assertThatThrownBy(
      () -> 상품.changePrice(MINUS_PRICE)
    ).isInstanceOf(IllegalArgumentException.class);
  }
}
