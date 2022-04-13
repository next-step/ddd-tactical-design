package kitchenpos.products.tobe.domain.test;

import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.Profanities;
import kitchenpos.products.tobe.domain.fixture.FakeProfanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {
  private static final Profanities profanities = new FakeProfanities();

  @Test
  @DisplayName("상품 이름을 등록할 수 있습니다.")
  void test1() {
    assertDoesNotThrow(
      () -> new ProductName("치킨", profanities)
    );
  }

  @ParameterizedTest
  @DisplayName("상품 이름이 존재하지 않으면 IllegalArgumentException 예외 발생")
  @NullAndEmptySource
  void test2(String name) {
    assertThatThrownBy(
      () -> new ProductName(name, profanities)
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("상품의 이름에는 비속어가 포함되면 IllegalArgumentException 예외 발생")
  void test7() {
    assertThatThrownBy(
      () -> new ProductName("욕설", profanities)
    ).isInstanceOf(IllegalArgumentException.class);
  }
}
