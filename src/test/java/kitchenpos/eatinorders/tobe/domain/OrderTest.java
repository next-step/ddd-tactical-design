package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

  @DisplayName("매장 테이블과 1개 이상 등록된 메뉴로 매장 주문을 등록할 수 있다.")
  @Test
  void createEatInOrder() {
    assertThatNoException()
        .isThrownBy(() -> new Order(
            UUID.randomUUID(),
            UUID.randomUUID(),
            List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
        ));
  }

  @DisplayName("매장 주문은 메뉴가 없으면 등록할 수 없다.")
  @Test
  void createOrderMenuNotValid() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> new Order(
            UUID.randomUUID(),
            UUID.randomUUID(),
            List.of(new OrderLineItem(null, BigDecimal.valueOf(16_000L), 3))
        ));
  }

  @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
  @ValueSource(longs = -1L)
  @ParameterizedTest
  void createEatInOrder(final long quantity) {
    assertThatNoException()
        .isThrownBy(() -> new Order(
            UUID.randomUUID(),
            UUID.randomUUID(),
            List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), quantity))
        ));
  }

  @DisplayName("주문을 접수한다.")
  @Test
  void accept() {
    Order order = new Order(
        UUID.randomUUID(),
        UUID.randomUUID(),
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );
    order.accept();
  }

  @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
  @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void accept_OnlyWaitingStateOrder(final OrderStatus status) {
    Order order = new Order(
        UUID.randomUUID(),
        UUID.randomUUID(),
        status,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );

    assertThatIllegalStateException()
        .isThrownBy(order::accept);
  }

  @DisplayName("주문을 서빙한다.")
  @Test
  void serve() {
    Order order = new Order(
        UUID.randomUUID(),
        UUID.randomUUID(),
        OrderStatus.ACCEPTED,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );
    order.serve();
  }

  @DisplayName("접수된 주문만 서빙할 수 있다.")
  @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void serve_OnlyAcceptedOrder(final OrderStatus status) {
    Order order = new Order(
        UUID.randomUUID(),
        UUID.randomUUID(),
        status,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );

    assertThatIllegalStateException()
        .isThrownBy(order::serve);
  }

  @DisplayName("주문을 완료한다.")
  @Test
  void complete() {
    Order order = new Order(
        UUID.randomUUID(),
        UUID.randomUUID(),
        OrderStatus.SERVED,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );
    order.complete();
  }

}