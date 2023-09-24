package kitchenpos.takeoutorders.tobe.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.sharedkernel.OrderStatus;
import kitchenpos.takeoutorders.tobe.application.dto.OrderLineItemRequest;
import kitchenpos.takeoutorders.tobe.application.dto.TakeOutOrderRequest;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrder;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrderRepository;
import kitchenpos.takeoutorders.tobe.infra.InMemoryTakeOutOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.*;
import static kitchenpos.takeoutorders.tobe.TakeOutOrderFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TakeOutOrderServiceTest {
    private TakeOutOrderRepository takeOutOrderRepository;
    private MenuRepository menuRepository;
    private TakeOutOrderService sut;

    @BeforeEach
    void setUp() {
        takeOutOrderRepository = new InMemoryTakeOutOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        sut = new TakeOutOrderService(takeOutOrderRepository, menuRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createTakeOutOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TakeOutOrderRequest expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final TakeOutOrder actual = sut.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull()
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemRequest> orderLineItems) {
        TakeOutOrderRequest expected = createOrderRequest(orderLineItems);
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("주문 항목의 수량은 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutTakeOutOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TakeOutOrderRequest expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        final TakeOutOrder actual = sut.create(expected);
        assertThat(actual).isNotNull();
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final TakeOutOrderRequest expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final TakeOutOrderRequest expected = createOrderRequest(
            createOrderLineItemRequest(menuId, 16_000L, 3L)
        );
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = takeOutOrderRepository.save(takeOutOrder(menu)).getId();
        final TakeOutOrder actual = sut.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void acceptFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = takeOutOrderRepository.save(acceptedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = takeOutOrderRepository.save(acceptedOrder(menu)).getId();
        final TakeOutOrder actual = sut.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serveFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = takeOutOrderRepository.save(servedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Menu menu = menuRepository.save(menu());
        final TakeOutOrder expected = takeOutOrderRepository.save(servedOrder(menu));
        final TakeOutOrder actual = sut.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @Test
    void completeFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = takeOutOrderRepository.save(completedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        takeOutOrderRepository.save(takeOutOrder(menu()));
        takeOutOrderRepository.save(takeOutOrder());
        final List<TakeOutOrder> actual = sut.findAll();
        assertThat(actual).hasSize(2);
    }

    private TakeOutOrderRequest createOrderRequest(final OrderLineItemRequest... orderLineItems) {
        return new TakeOutOrderRequest(Arrays.asList(orderLineItems));
    }

    private TakeOutOrderRequest createOrderRequest(final List<OrderLineItemRequest> orderLineItems) {
        return new TakeOutOrderRequest(orderLineItems);
    }

    private static OrderLineItemRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemRequest(
            quantity,
            menuId,
            price
        );
    }
}
