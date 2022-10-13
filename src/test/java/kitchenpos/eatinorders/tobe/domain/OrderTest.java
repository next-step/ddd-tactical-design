package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

  private OrderValidator orderValidator;
  private MenuRepository menuRepository;
  private OrderRepository orderRepository;
  private OrderTableRepository orderTableRepository;
  private OrderTableCleaner orderTableCleaner;

  @BeforeEach
  void setUp() {
    menuRepository = new InMemoryMenuRepository();
    orderRepository = new InMemoryOrderRepository();
    orderTableRepository = new InMemoryOrderTableRepository();
    orderValidator = new DefaultOrderValidator(
        menuRepository,
        orderRepository,
        orderTableRepository
    );
    orderTableCleaner = new OrderTableCleaner(
        orderRepository,
        orderTableRepository
    );
  }

  @DisplayName("매장 테이블과 1개 이상 등록된 메뉴로 매장 주문을 등록할 수 있다.")
  @Test
  void createEatInOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();

    assertThatNoException()
        .isThrownBy(() -> new Order(
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of(new OrderLineItem(menuId, BigDecimal.valueOf(16_000L), 3))
            ).place(orderValidator)
        );
  }

  @DisplayName("매장 주문은 메뉴가 없으면 등록할 수 없다.")
  @MethodSource("orderLineItems")
  @ParameterizedTest
  void createOrderMenuNotValid(final List<OrderLineItem> orderLineItems) {
    assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(() -> new Order(
                UUID.randomUUID(),
                UUID.randomUUID(),
                orderLineItems
            ).place(orderValidator)
        );
  }

  private static List<Arguments> orderLineItems() {
    return Arrays.asList(
        null,
        Arguments.of(Collections.emptyList()),
        Arguments.of(
            List.of(
                new OrderLineItem(
                    INVALID_ID,
                    Price.from(16_000L),
                    Quantity.from(3)
                )
            )
        )
    );
  }

  @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
  @Test
  void createNotDisplayedMenuOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
    assertThatIllegalStateException()
        .isThrownBy(() -> new Order(
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of(new OrderLineItem(menuId, BigDecimal.valueOf(16_000L), 3))
            ).place(orderValidator)
        );
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
    OrderTable orderTable = orderTableRepository.save(new OrderTable(UUID.randomUUID(), "9번", 5));
    Order order = new Order(
        UUID.randomUUID(),
        orderTable.getId(),
        OrderStatus.SERVED,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );
    order.complete(orderTableCleaner);
  }

  @DisplayName("서빙된 주문만 완료할 수 있다.")
  @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void serve_OnlyServedOrder(final OrderStatus status) {
    OrderTable orderTable = orderTableRepository.save(new OrderTable(UUID.randomUUID(), "9번", 5));
    Order order = new Order(
        UUID.randomUUID(),
        orderTable.getId(),
        status,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );

    assertThatIllegalStateException()
        .isThrownBy(() -> order.complete(orderTableCleaner));
  }

  @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
  @Test
  void completeEatInOrder() {
    OrderTable orderTable = orderTableRepository.save(new OrderTable(UUID.randomUUID(), "9번", 5));
    Order order = new Order(
        UUID.randomUUID(),
        orderTable.getId(),
        OrderStatus.SERVED,
        List.of(new OrderLineItem(UUID.randomUUID(), BigDecimal.valueOf(16_000L), 3))
    );
    order.complete(orderTableCleaner);
    OrderTable actual = orderTableRepository.findById(order.getOrderTableId()).orElseThrow();
    assertAll(
        () -> assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED),
        () -> assertThat(actual.isOccupied()).isFalse(),
        () -> assertThat(actual.getNumberOfGuests()).isEqualTo(NumberOfGuest.EMPTY)
    );
  }

}