package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

  @Test
  @DisplayName("주문 테이블을 등록할 수 있다.")
  void create() {
    assertThatNoException()
        .isThrownBy(() -> new OrderTable(UUID.randomUUID(), "9번", 5));
  }
}