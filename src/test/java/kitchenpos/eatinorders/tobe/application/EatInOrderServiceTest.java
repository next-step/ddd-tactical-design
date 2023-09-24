package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.tobe.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.infra.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderTableRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.sharedkernel.OrderStatus;
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
import static kitchenpos.eatinorders.tobe.EatInOrderFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderServiceTest {
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService sut;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        sut = new EatInOrderService(eatInOrderRepository, menuRepository, orderTableRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable()).getId();
        final EatInOrderRequest expected = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final EatInOrder actual = sut.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItemList()).hasSize(1),
            () -> assertThat(actual.getOrderTableId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItemRequest> orderLineItems) {
        final UUID orderTableId = orderTableRepository.save(orderTable()).getId();
        final EatInOrderRequest expected = createOrderRequest(orderTableId, orderLineItems);
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
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable()).getId();
        final EatInOrderRequest expected = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        final EatInOrder actual = sut.create(expected);
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다")
    void emptyOrderTable() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(0, false)).getId();
        final EatInOrderRequest expected = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable()).getId();
        final EatInOrderRequest expected = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable()).getId();
        final EatInOrderRequest expected = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, 16_000L, 3L)
        );
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = eatInOrderRepository.save(eatInOrder(menu)).getId();
        final EatInOrder actual = sut.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void acceptFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = eatInOrderRepository.save(acceptedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = eatInOrderRepository.save(acceptedOrder(menu)).getId();
        final EatInOrder actual = sut.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serveFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = eatInOrderRepository.save(servedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final Menu menu = menuRepository.save(menu());
        final OrderTable orderTable = orderTableRepository.save(orderTable());
        final EatInOrder expected = eatInOrderRepository.save(servedOrder(menu, orderTable));
        final EatInOrder actual = sut.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @Test
    void completeFailed() {
        final Menu menu = menuRepository.save(menu());
        final UUID orderId = eatInOrderRepository.save(completedOrder(menu)).getId();
        assertThatThrownBy(() -> sut.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        eatInOrderRepository.save(eatInOrder(orderTable(4, true)));
        eatInOrderRepository.save(eatInOrder());
        final List<EatInOrder> actual = sut.findAll();
        assertThat(actual).hasSize(2);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final Menu menu = menuRepository.save(menu());
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final EatInOrder expected = eatInOrderRepository.save(servedOrder(menu, orderTable));
        final EatInOrder actual = sut.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(new NumberOfGuests(0))
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final Menu menu = menuRepository.save(menu());
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        eatInOrderRepository.save(eatInOrder(orderTable));
        final EatInOrder expected = eatInOrderRepository.save(servedOrder(menu, orderTable));
        final EatInOrder actual = sut.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
            () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(new NumberOfGuests(4))
        );
    }

    private EatInOrderRequest createOrderRequest(final UUID orderTableId, final OrderLineItemRequest... orderLineItems) {
        return new EatInOrderRequest(Arrays.asList(orderLineItems), orderTableId);
    }

    private EatInOrderRequest createOrderRequest(final UUID orderTableId, final List<OrderLineItemRequest> orderLineItems) {
        return new EatInOrderRequest(orderLineItems, orderTableId);
    }

    private static OrderLineItemRequest createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItemRequest(
            quantity,
            menuId,
            price
        );
    }
}
