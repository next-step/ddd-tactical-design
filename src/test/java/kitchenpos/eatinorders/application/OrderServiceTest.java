package kitchenpos.eatinorders.application;

class OrderServiceTest {
  /*private OrderRepository orderRepository;
  private MenuRepository menuRepository;
  private OrderTableRepository orderTableRepository;
  private FakeKitchenridersClient kitchenridersClient;
  private OrderService orderService;

  private static List<Arguments> orderLineItems() {
    return Arrays.asList(
        null,
        Arguments.of(Collections.emptyList()),
        Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L))));
  }

  private static OrderLineItem createOrderLineItemRequest(
      final UUID menuId, final long price, final long quantity) {
    final OrderLineItem orderLineItem = new OrderLineItem();
    orderLineItem.setSeq(new Random().nextLong());
    orderLineItem.setMenuId(menuId);
    orderLineItem.setPrice(BigDecimal.valueOf(price));
    orderLineItem.setQuantity(quantity);
    return orderLineItem;
  }

  @BeforeEach
  void setUp() {
    orderRepository = new InMemoryOrderRepository();
    menuRepository = new InMemoryMenuRepository();
    orderTableRepository = new InMemoryOrderTableRepository();
    kitchenridersClient = new FakeKitchenridersClient();
    orderService =
        new OrderService(
            orderRepository, menuRepository, orderTableRepository, kitchenridersClient);
  }

  @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
  @Test
  void createDeliveryOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(
            OrderType.DELIVERY, "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L));
    final EatInOrder actual = orderService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.WAITING),
        () -> assertThat(actual.getOrderDateTime()).isNotNull(),
        () -> assertThat(actual.getOrderLineItems()).hasSize(1),
        () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress()));
  }

  @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
  @Test
  void createTakeoutOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
    final EatInOrder actual = orderService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.WAITING),
        () -> assertThat(actual.getOrderDateTime()).isNotNull(),
        () -> assertThat(actual.getOrderLineItems()).hasSize(1));
  }

  @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
  @Test
  void createEatInOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
    final EatInOrder expected =
        createOrderRequest(
            OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
    final EatInOrder actual = orderService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.WAITING),
        () -> assertThat(actual.getOrderDateTime()).isNotNull(),
        () -> assertThat(actual.getOrderLineItems()).hasSize(1),
        () -> assertThat(actual.getOrderTable().getId()).isEqualTo(expected.getOrderTableId()));
  }

  @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
  @NullSource
  @ParameterizedTest
  void create(final OrderType type) {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(type, createOrderLineItemRequest(menuId, 19_000L, 3L));
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴가 없으면 등록할 수 없다.")
  @MethodSource("orderLineItems")
  @ParameterizedTest
  void create(final List<OrderLineItem> orderLineItems) {
    final EatInOrder expected = createOrderRequest(OrderType.TAKEOUT, orderLineItems);
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
  @ValueSource(longs = -1L)
  @ParameterizedTest
  void createEatInOrder(final long quantity) {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
    final EatInOrder expected =
        createOrderRequest(
            OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity));
    assertDoesNotThrow(() -> orderService.create(expected));
  }

  @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
  @ValueSource(longs = -1L)
  @ParameterizedTest
  void createWithoutEatInOrder(final long quantity) {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(
            OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, quantity));
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create(final String deliveryAddress) {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(
            OrderType.DELIVERY, deliveryAddress, createOrderLineItemRequest(menuId, 19_000L, 3L));
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
  @Test
  void createEmptyTableEatInOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
    final EatInOrder expected =
        createOrderRequest(
            OrderType.EAT_IN, orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
  @Test
  void createNotDisplayedMenuOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 19_000L, 3L));
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
  @Test
  void createNotMatchedMenuPriceOrder() {
    final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
    final EatInOrder expected =
        createOrderRequest(OrderType.TAKEOUT, createOrderLineItemRequest(menuId, 16_000L, 3L));
    assertThatThrownBy(() -> orderService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("주문을 접수한다.")
  @Test
  void accept() {
    final UUID orderId =
        orderRepository.save(order(DeliveryOrderStatus.WAITING, orderTable(true, 4))).getId();
    final EatInOrder actual = orderService.accept(orderId);
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED);
  }

  @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
  @EnumSource(value = DeliveryOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void accept(final DeliveryOrderStatus status) {
    final UUID orderId = orderRepository.save(order(status, orderTable(true, 4))).getId();
    assertThatThrownBy(() -> orderService.accept(orderId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
  @Test
  void acceptDeliveryOrder() {
    final UUID orderId =
        orderRepository.save(order(DeliveryOrderStatus.WAITING, "서울시 송파구 위례성대로 2")).getId();
    final EatInOrder actual = orderService.accept(orderId);
    assertAll(
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED),
        () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(orderId),
        () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2"));
  }

  @DisplayName("주문을 서빙한다.")
  @Test
  void serve() {
    final UUID orderId = orderRepository.save(order(DeliveryOrderStatus.ACCEPTED)).getId();
    final EatInOrder actual = orderService.serve(orderId);
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.SERVED);
  }

  @DisplayName("접수된 주문만 서빙할 수 있다.")
  @EnumSource(value = DeliveryOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void serve(final DeliveryOrderStatus status) {
    final UUID orderId = orderRepository.save(order(status)).getId();
    assertThatThrownBy(() -> orderService.serve(orderId)).isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 배달한다.")
  @Test
  void startDelivery() {
    final UUID orderId = orderRepository.save(order(DeliveryOrderStatus.SERVED, "서울시 송파구 위례성대로 2")).getId();
    final EatInOrder actual = orderService.startDelivery(orderId);
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERING);
  }

  @DisplayName("배달 주문만 배달할 수 있다.")
  @Test
  void startDeliveryWithoutDeliveryOrder() {
    final UUID orderId = orderRepository.save(order(DeliveryOrderStatus.SERVED)).getId();
    assertThatThrownBy(() -> orderService.startDelivery(orderId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("서빙된 주문만 배달할 수 있다.")
  @EnumSource(value = DeliveryOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void startDelivery(final DeliveryOrderStatus status) {
    final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
    assertThatThrownBy(() -> orderService.startDelivery(orderId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 배달 완료한다.")
  @Test
  void completeDelivery() {
    final UUID orderId =
        orderRepository.save(order(DeliveryOrderStatus.DELIVERING, "서울시 송파구 위례성대로 2")).getId();
    final EatInOrder actual = orderService.completeDelivery(orderId);
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERED);
  }

  @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
  @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void completeDelivery(final DeliveryOrderStatus status) {
    final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
    assertThatThrownBy(() -> orderService.completeDelivery(orderId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문을 완료한다.")
  @Test
  void complete() {
    final EatInOrder expected = orderRepository.save(order(DeliveryOrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
    final EatInOrder actual = orderService.complete(expected.getId());
    assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.COMPLETED);
  }

  @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
  @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void completeDeliveryOrder(final DeliveryOrderStatus status) {
    final UUID orderId = orderRepository.save(order(status, "서울시 송파구 위례성대로 2")).getId();
    assertThatThrownBy(() -> orderService.complete(orderId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
  @EnumSource(value = DeliveryOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void completeTakeoutAndEatInOrder(final DeliveryOrderStatus status) {
    final UUID orderId = orderRepository.save(order(status)).getId();
    assertThatThrownBy(() -> orderService.complete(orderId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
  @Test
  void completeEatInOrder() {
    final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
    final EatInOrder expected = orderRepository.save(order(DeliveryOrderStatus.SERVED, orderTable));
    final EatInOrder actual = orderService.complete(expected.getId());
    assertAll(
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.COMPLETED),
        () ->
            assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied())
                .isFalse(),
        () ->
            assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests())
                .isEqualTo(0));
  }

  @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
  @Test
  void completeNotTable() {
    final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
    orderRepository.save(order(DeliveryOrderStatus.ACCEPTED, orderTable));
    final EatInOrder expected = orderRepository.save(order(DeliveryOrderStatus.SERVED, orderTable));
    final EatInOrder actual = orderService.complete(expected.getId());
    assertAll(
        () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.COMPLETED),
        () ->
            assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied())
                .isTrue(),
        () ->
            assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests())
                .isEqualTo(4));
  }

  @DisplayName("주문의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
    orderRepository.save(order(DeliveryOrderStatus.SERVED, orderTable));
    orderRepository.save(order(DeliveryOrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
    final List<EatInOrder> actual = orderService.findAll();
    assertThat(actual).hasSize(2);
  }

  private EatInOrder createOrderRequest(
      final OrderType type, final String deliveryAddress, final OrderLineItem... orderLineItems) {
    final EatInOrder order = new EatInOrder();
    order.setType(type);
    order.setDeliveryAddress(deliveryAddress);
    order.setOrderLineItems(Arrays.asList(orderLineItems));
    return order;
  }

  private EatInOrder createOrderRequest(
      final OrderType orderType, final OrderLineItem... orderLineItems) {
    return createOrderRequest(orderType, Arrays.asList(orderLineItems));
  }

  private EatInOrder createOrderRequest(
      final OrderType orderType, final List<OrderLineItem> orderLineItems) {
    final EatInOrder order = new EatInOrder();
    order.setType(orderType);
    order.setOrderLineItems(orderLineItems);
    return order;
  }

  private EatInOrder createOrderRequest(
      final OrderType type, final UUID orderTableId, final OrderLineItem... orderLineItems) {
    final EatInOrder order = new EatInOrder();
    order.setType(type);
    order.setOrderTableId(orderTableId);
    order.setOrderLineItems(Arrays.asList(orderLineItems));
    return order;
  }*/
}
