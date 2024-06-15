package kitchenpos.eatinorders.domain.eatinorder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderLineItemTest {
  @DisplayName("주문 메뉴를 생성할 수 있다.")
  @Test
  void creatOrderLineItem() {
    OrderLineItem actual = OrderLineItem.of(UUID.randomUUID(), 10_000L, 2L);

    assertAll(
            () -> assertThat(actual.getMenuId()).isNotNull(),
            () -> assertThat(actual.getPrice()).isEqualTo(10_000L),
            () -> assertThat(actual.getQuantity()).isEqualTo(2L)
    );
  }
}
