package kitchenpos.takeoutorders.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.dto.OrderLineItemCreateRequest;
import kitchenpos.support.infra.MenuClientImpl;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderRepository;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;
import kitchenpos.takeoutorders.dto.TakeoutOrderCreateRequest;
import kitchenpos.takeoutorders.dto.TakeoutOrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.Fixtures.INVALID_ID;
import static kitchenpos.fixture.MenuFixture.menu;
import static kitchenpos.fixture.MenuFixture.menuProduct;
import static kitchenpos.fixture.TakeoutOrderFixture.takeoutOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TakeoutOrderServiceTest {
    private TakeoutOrderRepository orderRepository;
    private MenuRepository menuRepository;
    private TakeoutOrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryTakeoutOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        MenuClient menuClient = new MenuClientImpl(menuRepository);
        orderService = new TakeoutOrderService(orderRepository, menuClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TakeoutOrderCreateRequest expected = createOrderRequest(3L, menuId);
        final TakeoutOrderResponse actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id()).isNotNull(),
            () -> assertThat(actual.status()).isEqualTo(TakeoutOrderStatus.WAITING),
            () -> assertThat(actual.orderDateTime()).isNotNull(),
            () -> assertThat(actual.orderLineItems()).hasSize(1)
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemCreateRequest> orderLineItems) {
        final TakeoutOrderCreateRequest expected = createOrderRequest(orderLineItems);
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TakeoutOrderCreateRequest expected = createOrderRequest(quantity, menuId);
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final TakeoutOrderCreateRequest expected = createOrderRequest(3L, menuId);
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TakeoutOrderCreateRequest expected = createOrderRequest(createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(takeoutOrder(TakeoutOrderStatus.WAITING)).getId();
        final TakeoutOrderResponse actual = orderService.accept(orderId);
        assertThat(actual.status()).isEqualTo(TakeoutOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final TakeoutOrderStatus status) {
        final UUID orderId = orderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(takeoutOrder(TakeoutOrderStatus.ACCEPTED)).getId();
        final TakeoutOrderResponse actual = orderService.serve(orderId);
        assertThat(actual.status()).isEqualTo(TakeoutOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final TakeoutOrderStatus status) {
        final UUID orderId = orderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final TakeoutOrder expected = orderRepository.save(takeoutOrder(TakeoutOrderStatus.SERVED));
        final TakeoutOrderResponse actual = orderService.complete(expected.getId());
        assertThat(actual.status()).isEqualTo(TakeoutOrderStatus.COMPLETED);
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final TakeoutOrderStatus status) {
        final UUID orderId = orderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderRepository.save(takeoutOrder(TakeoutOrderStatus.SERVED));
        orderRepository.save(takeoutOrder(TakeoutOrderStatus.COMPLETED));
        final List<TakeoutOrderResponse> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private TakeoutOrderCreateRequest createOrderRequest(final OrderLineItemCreateRequest... orderLineItems) {
        return createOrderRequest(Arrays.asList(orderLineItems));
    }

    private static TakeoutOrderCreateRequest createOrderRequest(long quantity, UUID menuId) {
        return new TakeoutOrderCreateRequest(List.of(createOrderLineItemRequest(menuId, 19_000L, quantity)));
    }

    private static TakeoutOrderCreateRequest createOrderRequest(List<OrderLineItemCreateRequest> orderLineItems) {
        return new TakeoutOrderCreateRequest(orderLineItems);
    }

    private static OrderLineItemCreateRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemCreateRequest(menuId, BigDecimal.valueOf(price), quantity);
    }
}
