package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kitchenpos.products.tobe.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {
  @DisplayName("상품 이름을 등록할 수 있다.")
  @Test
  void create() {
    String expected = "상품명";
    ProductName productName = ProductFixture.createProductName(expected);
    assertThat(productName.getName()).isEqualTo(expected);
  }

  @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
  @NullSource
  @ValueSource(strings = {"욕설", "비속어"})
  @ParameterizedTest
  void create(String expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> ProductFixture.createProductName(expected));
  }
}
