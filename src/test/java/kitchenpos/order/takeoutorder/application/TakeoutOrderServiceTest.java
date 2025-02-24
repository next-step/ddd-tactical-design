package kitchenpos.order.takeoutorder.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.order;
import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.ACCEPTED;
import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.SERVED;
import static kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menu.infra.InMemoryMenuRepository;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderLineItem;
import kitchenpos.order.common.domain.OrderRepository;
import kitchenpos.order.common.infra.InMemoryOrderRepository;
import kitchenpos.order.takeoutorder.domain.TakeoutOrder;
import kitchenpos.order.takeoutorder.domain.TakeoutOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class TakeoutOrderServiceTest {

    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private TakeoutOrderService takeoutOrderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        takeoutOrderService = new DefaultTakeoutOrderService(orderRepository, menuRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(createOrderLineItemRequest(menuId, 19_000L, 3L));
        final TakeoutOrder actual = (TakeoutOrder) takeoutOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final Order expected = createOrderRequest(orderLineItems);
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final Order expected = createOrderRequest(createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final Order expected = createOrderRequest(createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(WAITING)).getId();
        final TakeoutOrder actual = (TakeoutOrder) takeoutOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final TakeoutOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> takeoutOrderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(ACCEPTED)).getId();
        final TakeoutOrder actual = (TakeoutOrder) takeoutOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final TakeoutOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> takeoutOrderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutOrder(final TakeoutOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status)).getId();
        assertThatThrownBy(() -> takeoutOrderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    private TakeoutOrder createOrderRequest(final OrderLineItem... orderLineItems) {
        return createOrderRequest(Arrays.asList(orderLineItems));
    }

    private TakeoutOrder createOrderRequest(final List<OrderLineItem> orderLineItems) {
        final TakeoutOrder order = new TakeoutOrder();
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    private static OrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenuId(menuId);
        orderLineItem.setPrice(BigDecimal.valueOf(price));
        orderLineItem.setQuantity(quantity);
        return orderLineItem;
    }
}
