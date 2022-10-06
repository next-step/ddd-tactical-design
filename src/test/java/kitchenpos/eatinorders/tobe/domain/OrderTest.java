package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

  @DisplayName("매장 테이블과 1개 이상 등록된 메뉴로 매장 주문을 등록할 수 있다.")
  @Test
  void createOrder() {
    assertThatNoException()
        .isThrownBy(() -> new Order(
            UUID.randomUUID(),
            UUID.randomUUID(),
            List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
        ));

  }
}