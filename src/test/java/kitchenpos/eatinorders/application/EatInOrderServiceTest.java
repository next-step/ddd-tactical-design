package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.application.EatInOrderFixtures.orderTable;
import static kitchenpos.menus.tobe.MenuFixtures.menu;
import static kitchenpos.menus.tobe.MenuFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.application.InMemoryMenuRepository;
import kitchenpos.products.tobe.application.FakeEatInOrderApplicationEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.ApplicationEventPublisher;

class EatInOrderServiceTest {
  private EatInOrderRepository orderRepository;
  private MenuRepository menuRepository;
  private EatInOrderTableRepository orderTableRepository;
  private EatInOrderService orderService;
  private ApplicationEventPublisher applicationEventPublisher;

  private static List<Arguments> orderLineItems() {
    return Arrays.asList(
        null,
        Arguments.of(Collections.emptyList()),
        Arguments.of(
            Arrays.asList(createOrderLineItemRequest(null, BigDecimal.valueOf(19_000L), 3L))));
  }

  private static EatInOrderLineItemRequestDto createOrderLineItemRequest(
      final UUID menuId, final BigDecimal price, final long quantity) {
    return new EatInOrderLineItemRequestDto(menuId, price, quantity);
  }

  @BeforeEach
  void setUp() {
    orderRepository = new InMemoryEatInOrderRepository();
    menuRepository = new InMemoryMenuRepository();
    orderTableRepository = new InMemoryEatInOrderTableRepository();
    applicationEventPublisher = new FakeEatInOrderApplicationEventPublisher(menuRepository);
    orderService =
        new EatInOrderService(orderRepository, orderTableRepository, applicationEventPublisher);
  }

  @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
  @Test
  void createEatInOrder() {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    final long quantity = menu.getMenuProducts().getMenuProducts().getFirst().getQuantity();

    final EatInOrderLineItemRequestDto eatInOrderLineItemRequestDto =
        new EatInOrderLineItemRequestDto(menuId, BigDecimal.valueOf(19_000L), quantity);

    final EatInOrderTable eatInOrderTable = orderTable("1번");
    eatInOrderTable.sit(2);
    orderTableRepository.save(eatInOrderTable);

    final EatInOrderRequestDto expected =
        new EatInOrderRequestDto(eatInOrderTable.getId(), List.of(eatInOrderLineItemRequestDto));

    final EatInOrderResponseDto actual = orderService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getOrderId()).isNotNull(),
        () -> assertThat(actual.getOrderDateTime()).isNotNull(),
        () -> assertThat(actual.getOrderLineItemResponseDtos()).hasSize(1));
  }

  @DisplayName("메뉴가 없으면 등록할 수 없다.")
  @MethodSource("orderLineItems")
  @ParameterizedTest
  void create(final List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDtos) {
    final EatInOrderTable eatInOrderTable = orderTable("1번");
    eatInOrderTable.sit(2);
    orderTableRepository.save(eatInOrderTable);

    final EatInOrderRequestDto expected =
        new EatInOrderRequestDto(eatInOrderTable.getId(), eatInOrderLineItemRequestDtos);

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
  @Test
  void createEmptyTableEatInOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final UUID orderTableId = orderTableRepository.save(orderTable("1번", false, 0)).getId();

    final EatInOrderRequestDto expected =
        EatInOrderFixtures.eatInOrderRequestDto(
            orderTableId,
            EatInOrderFixtures.eatInOrderLineItemRequestDto(
                menuId, BigDecimal.valueOf(19_000L), 3L));

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 접수한다.")
  @Test
  void accept() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    final EatInOrderResponseDto actual = orderService.accept(order.getId());
    assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
  }

  @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
  @Test
  void accept_fail() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.accept(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 서빙한다.")
  @Test
  void serve() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    final EatInOrderResponseDto actual = orderService.serve(order.getId());
    assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
  }

  @DisplayName("접수된 주문만 서빙할 수 있다.")
  @Test
  void serve_fail() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();
    order.serve();

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.serve(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 완료한다.")
  @Test
  void complete() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();
    order.serve();

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    final EatInOrderResponseDto actual = orderService.complete(order.getId());
    assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
  }

  @DisplayName("서빙된 주문만 완료할 수 있다.")
  @Test
  void completeTakeoutAndEatInOrder() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.complete(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
  @Test
  void completeEatInOrder() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();
    order.serve();

    orderTableRepository.save(order.getOrderTable());
    orderRepository.save(order);

    final EatInOrderResponseDto actual = orderService.complete(order.getId());
    assertAll(
        () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
        () ->
            assertThat(orderTableRepository.findById(actual.getOrderTableId()).get().getOccupied())
                .isFalse(),
        () ->
            assertThat(
                    orderTableRepository
                        .findById(actual.getOrderTableId())
                        .get()
                        .getNumberOfGuests())
                .isEqualTo(0));
  }

  @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
  @Test
  void completeNotTable() {
    final EatInOrder order = EatInOrderFixtures.order("1번");
    order.getOrderTable().sit(4);
    order.accepted();

    orderRepository.save(order);
    assertThatThrownBy(() -> orderService.complete(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    final EatInOrder order1 = EatInOrderFixtures.order("1번");
    order1.getOrderTable().sit(4);
    order1.accepted();
    final EatInOrder order2 = EatInOrderFixtures.order("2번");
    order2.getOrderTable().sit(4);
    order2.accepted();

    orderRepository.save(order1);
    orderRepository.save(order2);

    final List<EatInOrderResponseDto> actual = orderService.findAll();
    assertThat(actual).hasSize(2);
  }

  private EatInOrder createOrderRequest(
      final EatInOrderTable eatInOrderTable, final EatInOrderLineItem... orderLineItems) {
    return EatInOrder.createOrder(Arrays.stream(orderLineItems).toList(), eatInOrderTable);
  }
}
