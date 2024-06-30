package kitchenpos.deliveryorders.application;

import static kitchenpos.menus.tobe.MenuFixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.domain.DeliveryOrderStatus;
import kitchenpos.deliveryorders.domain.DeliveryOrderType;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.application.InMemoryMenuRepository;
import kitchenpos.products.tobe.application.FakeDeliveryOrderApplicationEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationEventPublisher;

class DeliveryOrderServiceTest {
  private DeliveryOrderRepository orderRepository;
  private MenuRepository menuRepository;
  private FakeKitchenridersClient kitchenridersClient;
  private DeliveryOrderService orderService;
  private ApplicationEventPublisher applicationEventPublisher;

  private static List<Arguments> orderLineItems() {
    return Arrays.asList(
        null,
        Arguments.of(Collections.emptyList()),
        Arguments.of(
            Arrays.asList(createOrderLineItemRequest(null, BigDecimal.valueOf(19_000L), 3L))));
  }

  private static DeliveryOrderLineItemRequestDto createOrderLineItemRequest(
      final UUID menuId, final BigDecimal price, final long quantity) {
    return new DeliveryOrderLineItemRequestDto(menuId, price, quantity);
  }

  private static DeliveryOrderRequestDto createDeliveryOrderRequestDto(
      final String deliveryAddress,
      final List<DeliveryOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos) {
    return new DeliveryOrderRequestDto(deliveryAddress, takeoutOrderLineItemRequestDtos);
  }

  private static DeliveryOrderRequestDto createDeliveryOrderRequestDto(
      final UUID menuId,
      final BigDecimal price,
      final long quantity,
      final String deliveryAddress) {
    return new DeliveryOrderRequestDto(
        deliveryAddress, List.of(new DeliveryOrderLineItemRequestDto(menuId, price, quantity)));
  }

  @BeforeEach
  void setUp() {
    orderRepository = new InMemoryDeliveryOrderRepository();
    menuRepository = new InMemoryMenuRepository();
    kitchenridersClient = new FakeKitchenridersClient();
    applicationEventPublisher = new FakeDeliveryOrderApplicationEventPublisher(menuRepository);
    orderService =
        new DeliveryOrderService(orderRepository, kitchenridersClient, applicationEventPublisher);
  }

  @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
  @Test
  void createDeliveryOrder() {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    final long quantity = menu.getMenuProducts().getMenuProducts().getFirst().getQuantity();
    menuRepository.save(menu);
    final DeliveryOrderRequestDto expected =
        createDeliveryOrderRequestDto(
            menuId, BigDecimal.valueOf(19_000L), quantity, "서울시 송파구 위례성대로 2");

    final DeliveryOrderResponseDto actual = orderService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getOrderId()).isNotNull(),
        () -> assertThat(actual.getType()).isEqualTo(DeliveryOrderType.DELIVERY),
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.WAITING),
        () -> assertThat(actual.getOrderDateTime()).isNotNull(),
        () -> assertThat(actual.getOrderLineItemResponseDtos()).hasSize(1));
  }

  @DisplayName("메뉴가 없으면 등록할 수 없다.")
  @MethodSource("orderLineItems")
  @ParameterizedTest
  void create(final List<DeliveryOrderLineItemRequestDto> orderLineItems) {
    final DeliveryOrderRequestDto expected =
        createDeliveryOrderRequestDto("서울시 송파구 위례성대로 2", orderLineItems);

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

    final DeliveryOrderRequestDto expected =
        createDeliveryOrderRequestDto(menuId, menu.getPrice(), quantity, "서울시 송파구 위례성대로 2");

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create(final String deliveryAddress) {
    final Menu menu = menu();
    final UUID menuId = menu.getId();
    final long quantity = menu.getMenuProducts().getMenuProducts().getFirst().getQuantity();
    menuRepository.save(menu);

    final DeliveryOrderRequestDto expected =
        createDeliveryOrderRequestDto(menuId, menu.getPrice(), quantity, deliveryAddress);

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

    final DeliveryOrderRequestDto expected =
        createDeliveryOrderRequestDto(menuId, menu.getPrice(), quantity, "서울시 송파구 위례성대로 2");
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

    final DeliveryOrderRequestDto expected =
        createDeliveryOrderRequestDto(
            menuId, BigDecimal.valueOf(16_000L), quantity, "서울시 송파구 위례성대로 2");

    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("주문을 접수한다.")
  @Test
  void accept() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    orderRepository.save(order);

    final DeliveryOrderResponseDto actual = orderService.accept(order.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED);
  }

  @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
  @Test
  void accept_fail() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.accept(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("배달 주문을 접수하면 배달 대행사를 호출한다.")
  @Test
  void acceptDeliveryOrder() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    orderRepository.save(order);

    final DeliveryOrderResponseDto actual = orderService.accept(order.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED);

    assertAll(
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED),
        () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(order.getId()),
        () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2"));
  }

  @DisplayName("주문을 서빙한다.")
  @Test
  void serve() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    orderRepository.save(order);

    final DeliveryOrderResponseDto actual = orderService.serve(order.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.SERVED);
  }

  @DisplayName("접수된 주문만 서빙할 수 있다.")
  @Test
  void serve_fail() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.serve(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 배달한다.")
  @Test
  void startDelivery() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    order.serve();
    orderRepository.save(order);

    final DeliveryOrderResponseDto actual = orderService.startDelivery(order.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERING);
  }

  @DisplayName("배달 주문만 배달할 수 있다.")
  @Test
  void startDeliveryWithoutDeliveryOrder() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.startDelivery(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("서빙된 주문만 배달할 수 있다.")
  @Test
  void startDelivery_fail() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.startDelivery(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 배달 완료한다.")
  @Test
  void completeDelivery() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    order.serve();
    order.startDelivery();
    orderRepository.save(order);

    final DeliveryOrderResponseDto actual = orderService.completeDelivery(order.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERED);
  }

  @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
  @Test
  void completeDelivery_fail() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.completeDelivery(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 완료한다.")
  @Test
  void complete() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    order.serve();
    order.startDelivery();
    order.completeDelivery();
    orderRepository.save(order);

    final DeliveryOrderResponseDto actual = orderService.complete(order.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.COMPLETED);
  }

  @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
  @Test
  void completeDeliveryOrder_fail() {
    final DeliveryOrder order = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order.accept(kitchenridersClient);
    orderRepository.save(order);

    assertThatThrownBy(() -> orderService.complete(order.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    final DeliveryOrder order1 = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order1.accept(kitchenridersClient);
    final DeliveryOrder order2 = DeliveryOrderFixtures.order("서울시 송파구 위례성대로 2");
    order2.accept(kitchenridersClient);

    orderRepository.save(order1);
    orderRepository.save(order2);

    final List<DeliveryOrderResponseDto> actual = orderService.findAll();
    assertThat(actual).hasSize(2);
  }
}
