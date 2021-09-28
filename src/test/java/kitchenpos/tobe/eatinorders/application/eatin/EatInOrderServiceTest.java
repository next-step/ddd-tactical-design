package kitchenpos.tobe.eatinorders.application.eatin;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.EMPTY_TABLE_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.HIDE_MENU_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.createOrderLineItemRequest;
import static kitchenpos.tobe.eatinorders.application.Fixtures.eatInOrder;
import static kitchenpos.tobe.eatinorders.application.Fixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.application.EatInOrderService;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.tobe.eatinorders.application.InMemoryMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EatInOrderServiceTest {
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService eatInOrderService;
    private FakeEventPublisher fakeEventPublisher = new FakeEventPublisher();

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new FakeOrderTableRepository();
        eatInOrderService = new EatInOrderService(eatInOrderRepository, menuRepository, orderTableRepository, fakeEventPublisher);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menu().getId();
        final UUID orderTableId = UUID.randomUUID();
        final EatInOrder expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final EatInOrder actual = eatInOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTableId()).isEqualTo(expected.getOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final EatInOrder expected = createOrderRequest(UUID.randomUUID(), orderLineItems);
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menu().getId();
        final EatInOrder expected = createOrderRequest(
                UUID.randomUUID(), createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> eatInOrderService.create(expected));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menu().getId();
        final EatInOrder expected = createOrderRequest(
                EMPTY_TABLE_ID, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final EatInOrder expected = createOrderRequest(UUID.randomUUID(), createOrderLineItemRequest(HIDE_MENU_ID, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menu().getId();
        final EatInOrder expected = createOrderRequest(UUID.randomUUID(), createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(EatInOrderStatus.WAITING, UUID.randomUUID())).getId();
        final EatInOrder actual = eatInOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final EatInOrderStatus status) {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(status, UUID.randomUUID())).getId();
        assertThatThrownBy(() -> eatInOrderService.accept(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, UUID.randomUUID())).getId();
        final EatInOrder actual = eatInOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final EatInOrderStatus status) {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(status, UUID.randomUUID())).getId();
        assertThatThrownBy(() -> eatInOrderService.serve(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(EatInOrderStatus.SERVED, UUID.randomUUID()));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final EatInOrderStatus status) {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(status, UUID.randomUUID())).getId();
        assertThatThrownBy(() -> eatInOrderService.complete(orderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 테이블 클린 이벤트를 발행한다.")
    @Test
    void completeEatInOrder() {
        final UUID orderTableId = UUID.randomUUID();
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(EatInOrderStatus.SERVED, orderTableId));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
                () -> assertThat(fakeEventPublisher.getCleanedTableId()).isEqualTo(orderTableId)
        );
    }
//
//    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
//    @Test
//    void completeNotTable() {
//        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 4));
//        eatInOrderRepository.save(order(EatInOrderStatus.ACCEPTED, orderTable));
//        final EatInOrder expected = eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable));
//        final EatInOrder actual = eatInOrderService.complete(expected.getId());
//        assertAll(
//                () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
//                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isEmpty()).isFalse(),
//                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuests()).isEqualTo(4)
//        );
//    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.SERVED, UUID.randomUUID()));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.WAITING, UUID.randomUUID()));
        final List<EatInOrder> actual = eatInOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private EatInOrder createOrderRequest(
            final UUID orderTableId,
            final OrderLineItem... orderLineItems
    ) {
        final EatInOrder order = new EatInOrder(Arrays.asList(orderLineItems), orderTableId);
        return order;
    }

    private EatInOrder createOrderRequest(
            final UUID orderTableId,
            final List<OrderLineItem> orderLineItems
    ) {
        final EatInOrder order = new EatInOrder(orderLineItems, orderTableId);
        return order;
    }


}
