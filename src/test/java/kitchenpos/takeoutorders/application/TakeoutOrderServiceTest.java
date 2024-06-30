package kitchenpos.takeoutorders.application;

import static kitchenpos.menus.tobe.MenuFixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.application.InMemoryMenuRepository;
import kitchenpos.products.tobe.application.FakeTakeoutOrderApplicationEventPublisher;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderRepository;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationEventPublisher;

class TakeoutOrderServiceTest {
  private TakeoutOrderRepository orderRepository;
  private MenuRepository menuRepository;
  private TakeoutOrderService orderService;
  private ApplicationEventPublisher applicationEventPublisher;

  private static List<Arguments> orderLineItems() {
    return Arrays.asList(
        null,
        Arguments.of(Collections.emptyList()),
        Arguments.of(
            Arrays.asList(createOrderLineItemRequest(null, BigDecimal.valueOf(19_000L), 3L))));
  }

  private static TakeoutOrderLineItemRequestDto createOrderLineItemRequest(
      final UUID menuId, final BigDecimal price, final long quantity) {
    return new TakeoutOrderLineItemRequestDto(menuId, price, quantity);
  }

  private static TakeoutOrderRequestDto createTakeoutOrderRequestDto(
      final List<TakeoutOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos) {
    return new TakeoutOrderRequestDto(takeoutOrderLineItemRequestDtos);
  }

  private static TakeoutOrderRequestDto createTakeoutOrderRequestDto(
      final UUID menuId, final BigDecimal price, final long quantity) {
    return new TakeoutOrderRequestDto(
        List.of(new TakeoutOrderLineItemRequestDto(menuId, price, quantity)));
  }

  @BeforeEach
  void setUp() {
    orderRepository = new InMemoryTakeoutOrderRepository();
    menuRepository = new InMemoryMenuRepository();
    applicationEventPublisher = new FakeTakeoutOrderApplicationEventPublisher(menuRepository);
    orderService = new TakeoutOrderService(orderRepository, applicationEventPublisher);
  }

  @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
  @Test
  void createTakeoutOrder() {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    final long quantity = menu.getMenuProducts().getMenuProducts().getFirst().getQuantity();
    menuRepository.save(menu);

    final TakeoutOrderRequestDto expected =
        createTakeoutOrderRequestDto(menuId, BigDecimal.valueOf(19_000L), quantity);

    final TakeoutOrderResponseDto actual = orderService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getOrderId()).isNotNull(),
        () -> assertThat(actual.getOrderDateTime()).isNotNull(),
        () -> assertThat(actual.getOrderLineItemResponseDtos()).hasSize(1));
  }

  @DisplayName("메뉴가 없으면 등록할 수 없다.")
  @MethodSource("orderLineItems")
  @ParameterizedTest
  void create(final List<TakeoutOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos) {
    final TakeoutOrderRequestDto expected =
        new TakeoutOrderRequestDto(takeoutOrderLineItemRequestDtos);

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("주문 항목의 수량은 0 이상이어야 한다.")
  @ValueSource(longs = -1L)
  @ParameterizedTest
  void createWithoutEatInOrder(final long quantity) {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    menuRepository.save(menu);

    final TakeoutOrderRequestDto expected =
        createTakeoutOrderRequestDto(menuId, BigDecimal.valueOf(19_000L), quantity);

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
  @Test
  void createNotDisplayedMenuOrder() {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    final long quantity = menu.getMenuProducts().getMenuProducts().getFirst().getQuantity();
    menu.hide();
    menuRepository.save(menu);

    final TakeoutOrderRequestDto expected =
        createTakeoutOrderRequestDto(menuId, BigDecimal.valueOf(19_000L), quantity);

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
  @Test
  void createNotMatchedMenuPriceOrder() {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    final long quantity = menu.getMenuProducts().getMenuProducts().getFirst().getQuantity();
    menuRepository.save(menu);

    final TakeoutOrderRequestDto expected =
        createTakeoutOrderRequestDto(menuId, BigDecimal.valueOf(16_000L), quantity);

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("주문을 접수한다.")
  @Test
  void accept() {
    final TakeoutOrder order = TakeoutOrderFixtures.order();

    orderRepository.save(order);

    final TakeoutOrderResponseDto actual = orderService.accept(order.getId());
    assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.ACCEPTED);
  }

  @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
  @Test
  void accept_fail() {
    final TakeoutOrder order = TakeoutOrderFixtures.order();
    order.accepted();
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.accept(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 서빙한다.")
  @Test
  void serve() {
    final TakeoutOrder order = TakeoutOrderFixtures.order();
    order.accepted();
    orderRepository.save(order);

    final TakeoutOrderResponseDto actual = orderService.serve(order.getId());
    assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.SERVED);
  }

  @DisplayName("접수된 주문만 서빙할 수 있다.")
  @Test
  void serve_fail() {
    final TakeoutOrder order = TakeoutOrderFixtures.order();
    order.accepted();
    order.serve();
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.serve(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 완료한다.")
  @Test
  void complete() {
    final TakeoutOrder order = TakeoutOrderFixtures.order();
    order.accepted();
    order.serve();
    orderRepository.save(order);

    final TakeoutOrderResponseDto actual = orderService.complete(order.getId());
    assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.COMPLETED);
  }

  @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
  @Test
  void completeTakeoutAndEatInOrder() {
    final TakeoutOrder order = TakeoutOrderFixtures.order();
    order.accepted();
    order.serve();
    order.completed();
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.complete(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    final TakeoutOrder order1 = TakeoutOrderFixtures.order();
    order1.accepted();
    final TakeoutOrder order2 = TakeoutOrderFixtures.order();
    order2.accepted();

    orderRepository.save(order1);
    orderRepository.save(order2);

    final List<TakeoutOrderResponseDto> actual = orderService.findAll();
    assertThat(actual).hasSize(2);
  }
}
