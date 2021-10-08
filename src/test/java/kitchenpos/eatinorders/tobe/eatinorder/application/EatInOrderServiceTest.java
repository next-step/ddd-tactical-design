package kitchenpos.eatinorders.tobe.eatinorder.application;

import kitchenpos.eatinorders.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.eatinorder.domain.MenuLoader;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.eatinorder.infra.FakeMenuClient;
import kitchenpos.eatinorders.tobe.eatinorder.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.CreateRequest;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.tobe.ordertable.application.OrderTableService;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.ordertable.infra.InMemoryOrderTableRepository;
import kitchenpos.fixture.EatInOrderFixture;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.OrderTableFixture;
import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("매장주문 응용 서비스(EatInOrderService)는")
class EatInOrderServiceTest {
    private OrderRepository orderRepository;
    private FakeMenuClient menuClient;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService orderService;
    private MenuLoader menuLoader = new MenuLoader();

    private OrderTable orderTable = OrderTableFixture.앉은테이블( 4);
    private long price = 19_000L;
    private MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록(price);
    private Menu menu = MenuFixture.메뉴(price, menuProducts);;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        menuClient = new FakeMenuClient();
        orderService = new EatInOrderService(orderRepository, new OrderTableService(orderTableRepository), menuLoader, menuClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuClient.save(menu).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable).getId();
        final CreateRequest expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final EatInOrder actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getOrderDateTime()).isNotNull()
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemCreateRequest> orderLineItems) {
        final CreateRequest expected = createOrderRequest(orderTableRepository.save(orderTable).getId(), orderLineItems);
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createOrderLineItemRequest(UUID.randomUUID(), 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuClient.save(menu).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable).getId();
        final CreateRequest expected = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> orderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuClient.save(menu).getId();
        final UUID orderTableId = orderTableRepository.save(OrderTableFixture.주문테이블()).getId();
        final CreateRequest expected = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuClient.save(MenuFixture.메뉴(19_000L, false, menuProducts)).getId();
        final CreateRequest expected = createOrderRequest(menuId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuClient.save(menu).getId();
        final CreateRequest expected = createOrderRequest(menuId, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(EatInOrderFixture.매장주문(OrderStatus.WAITING, orderTable)).getId();
        final EatInOrder actual = orderService.accept(orderId);
        assertThat(actual).isEqualTo(EatInOrderFixture.매장주문(OrderStatus.ACCEPTED, orderTable));
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final OrderStatus status) {
        final UUID orderId = orderRepository.save(EatInOrderFixture.매장주문(status, orderTable)).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(EatInOrderFixture.매장주문(OrderStatus.ACCEPTED, orderTable)).getId();
        final EatInOrder actual = orderService.serve(orderId);
        assertThat(actual).isEqualTo(EatInOrderFixture.매장주문(OrderStatus.SERVED, orderTable));
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final OrderStatus status) {
        final UUID orderId = orderRepository.save(EatInOrderFixture.매장주문(status, orderTable)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

//
//    @DisplayName("주문을 완료한다.")
//    @Test
//    void complete() {
//        final Order expected = orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
//        final Order actual = orderService.complete(expected.getId());
//        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
//    }
//
//
//    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
//    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
//    @ParameterizedTest
//    void completeTakeoutAndEatInOrder(final OrderStatus status) {
//        final UUID orderId = orderRepository.save(order(status)).getId();
//        assertThatThrownBy(() -> orderService.complete(orderId))
//            .isInstanceOf(IllegalStateException.class);
//    }
//
//    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
//    @Test
//    void completeEatInOrder() {
//        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 4));
//        final Order expected = orderRepository.save(order(OrderStatus.SERVED, orderTable));
//        final Order actual = orderService.complete(expected.getId());
//        assertAll(
//            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
//            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isEmpty()).isTrue(),
//            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(0)
//        );
//    }
//
//    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
//    @Test
//    void completeNotTable() {
//        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 4));
//        orderRepository.save(order(OrderStatus.ACCEPTED, orderTable));
//        final Order expected = orderRepository.save(order(OrderStatus.SERVED, orderTable));
//        final Order actual = orderService.complete(expected.getId());
//        assertAll(
//            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
//            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isEmpty()).isFalse(),
//            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(4)
//        );
//    }
//
//    @DisplayName("주문의 목록을 조회할 수 있다.")
//    @Test
//    void findAll() {
//        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 4));
//        orderRepository.save(order(OrderStatus.SERVED, orderTable));
//        orderRepository.save(order(OrderStatus.DELIVERED, "서울시 송파구 위례성대로 2"));
//        final List<Order> actual = orderService.findAll();
//        assertThat(actual).hasSize(2);
//    }

    private CreateRequest createOrderRequest(final UUID orderTableId, final OrderLineItemCreateRequest... orderLineItems) {
        return createOrderRequest(orderTableId, Arrays.asList(orderLineItems));
    }

    private CreateRequest createOrderRequest(final UUID orderTableId, final List<OrderLineItemCreateRequest> orderLineItems) {
        return new CreateRequest(orderTableId, orderLineItems);
    }

    private static OrderLineItemCreateRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemCreateRequest(menuId, quantity, BigDecimal.valueOf(price));
    }
}
