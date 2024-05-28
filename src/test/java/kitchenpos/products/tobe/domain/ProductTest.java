package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.application.FakePurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("상품 이름이 공백 또는 비속어일 경우")
  void case1(String name) {
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              Product.create(name, BigDecimal.valueOf(10_000L), new FakePurgomalumClient());
            });
  }

  @Test
  @DisplayName("상품 금액이 0원 이하일 경우")
  void case2() {
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              Product.create("상품", BigDecimal.valueOf(-10_000L), new FakePurgomalumClient());
            });
  }
}