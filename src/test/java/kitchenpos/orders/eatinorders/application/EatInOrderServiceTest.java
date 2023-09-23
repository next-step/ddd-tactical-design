package kitchenpos.orders.eatinorders.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderLineItemRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.orders.eatinorders.application.loader.impl.DefaultMenuLoader;
import kitchenpos.orders.eatinorders.application.loader.impl.DefaultOrderTableStatusLoader;
import kitchenpos.orders.eatinorders.domain.EatInOrder;
import kitchenpos.orders.eatinorders.domain.EatInOrderRepository;
import kitchenpos.orders.eatinorders.domain.EatInOrderStatus;
import kitchenpos.orders.eatinorders.exception.EatInOrderException;
import kitchenpos.orders.eatinorders.exception.EatInOrderLineItemException;
import kitchenpos.orders.ordertables.application.InMemoryOrderTableRepository;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.menus.application.fixtures.MenuFixture.*;
import static kitchenpos.orders.eatinorders.fixture.EatInOrderFixture.order;
import static kitchenpos.orders.ordertables.fixture.OrderTableFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("매장 주문 서비스")
class EatInOrderServiceTest {
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderService orderService;


    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        orderService = new EatInOrderService(
                eatInOrderRepository,
                new DefaultMenuLoader(menuRepository),
                new DefaultOrderTableStatusLoader(orderTableRepository));
    }

    @Nested
    @DisplayName("매장 주문 등록")
    class create {
        @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
        @Test
        void createEatInOrder() {
            final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct()));
            final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    createOrderLineItemRequest(menu.getIdValue(), 3L, 19_000L));

            final EatInOrderResponse actual = orderService.create(request);

            assertThat(actual).isNotNull();
            assertAll(
                    () -> assertThat(actual.getId()).isNotNull(),
                    () -> assertThat(actual.getEatInOrderStatus()).isEqualTo(EatInOrderStatus.WAITING),
                    () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                    () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                    () -> assertThat(actual.getOrderTableId()).isEqualTo(request.getOrderTableId())
            );
        }


        @DisplayName("메뉴가 없으면 등록할 수 없다.")
        @NullAndEmptySource
        @ParameterizedTest
        void create1(final List<EatInOrderLineItemRequest> eatInOrderLineItems) {
            final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    eatInOrderLineItems);

            assertThatThrownBy(() -> orderService.create(request))
                    .isInstanceOf(EatInOrderLineItemException.class);
        }

        @DisplayName("메뉴가 없으면 등록할 수 없다.")
        @Test
        void create2() {
            final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    createOrderLineItemRequest(INVALID_ID, 3L, 19_000L));

            assertThatThrownBy(() -> orderService.create(request))
                    .isInstanceOf(NoSuchElementException.class);
        }


        @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
        @ValueSource(longs = -1L)
        @ParameterizedTest
        void createEatInOrder(final long quantity) {
            final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct()));
            final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    createOrderLineItemRequest(menu.getIdValue(), quantity, 19_000L));

            assertDoesNotThrow(() -> orderService.create(request));
        }


        @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
        @Test
        void createEmptyTableEatInOrder() {
            final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct()));
            final OrderTable orderTable = orderTableRepository.save(orderTable(false, 0));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    createOrderLineItemRequest(menu.getIdValue(), 3L, 19_000L));

            assertThatThrownBy(() -> orderService.create(request))
                    .isInstanceOf(EatInOrderException.class);
        }

        @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
        @Test
        void createNotDisplayedMenuOrder() {
            final Menu menu = menuRepository.save(menu(19_000L, false, menuProduct()));
            final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    createOrderLineItemRequest(menu.getIdValue(), 3L, 19_000L));

            assertThatThrownBy(() -> orderService.create(request))
                    .isInstanceOf(EatInOrderLineItemException.class);
        }

        @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
        @Test
        void createNotMatchedMenuPriceOrder() {
            final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct()));
            final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));

            final EatInOrderRequest request = createOrderRequest(
                    orderTable.getIdValue(),
                    createOrderLineItemRequest(menu.getIdValue(), 3L, 18_000L));

            assertThatThrownBy(() -> orderService.create(request))
                    .isInstanceOf(EatInOrderLineItemException.class);
        }
    }

    @DisplayName("주문 접수")
    @Nested
    class accept {

        @DisplayName("주문을 접수한다.")
        @Test
        void accept1() {
            EatInOrder order = eatInOrderRepository.save(order(EatInOrderStatus.WAITING, orderTable(true, 4)));
            final EatInOrderResponse actual = orderService.accept(order.getId());

            assertThat(actual.getEatInOrderStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
        }

        @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
        @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
        @ParameterizedTest
        void accept2(final EatInOrderStatus status) {
            EatInOrder order = eatInOrderRepository.save(order(status, orderTable(true, 4)));

            assertThatThrownBy(() -> orderService.accept(order.getId()))
                    .isInstanceOf(EatInOrderException.class);
        }

    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        EatInOrder order = eatInOrderRepository.save(order(EatInOrderStatus.ACCEPTED, orderTable(true, 4)));
        final EatInOrderResponse actual = orderService.serve(order.getId());
        assertThat(actual.getEatInOrderStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final EatInOrderStatus status) {
        EatInOrder order = eatInOrderRepository.save(order(status, orderTable(true, 4)));
        assertThatThrownBy(() -> orderService.serve(order.getId()))
                .isInstanceOf(EatInOrderException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        EatInOrder order = eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable(true, 4)));
        final EatInOrderResponse actual = orderService.complete(order.getId());
        assertThat(actual.getEatInOrderStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final EatInOrderStatus status) {
        EatInOrder order = eatInOrderRepository.save(order(status, orderTable(true, 4)));
        assertThatThrownBy(() -> orderService.complete(order.getId()))
                .isInstanceOf(EatInOrderException.class);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable));
        eatInOrderRepository.save(order(EatInOrderStatus.ACCEPTED, orderTable));
        final List<EatInOrderResponse> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }


    private static EatInOrderLineItemRequest createOrderLineItemRequest(UUID menuId, long quantity, long price) {
        return new EatInOrderLineItemRequest(
                menuId,
                quantity,
                price);
    }

    private EatInOrderRequest createOrderRequest(UUID orderTableId, EatInOrderLineItemRequest... items) {
        return new EatInOrderRequest(orderTableId, Arrays.asList(items));
    }

    private EatInOrderRequest createOrderRequest(UUID orderTableId, List<EatInOrderLineItemRequest> items) {
        return new EatInOrderRequest(orderTableId, items);
    }
}
