package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.tobe.application.ToBeOrderService;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ToBeOrderServiceTest {
    private ToBeOrderRepository orderRepository;
    private ToBeMenuRepository menuRepository;
    private ToBeOrderTableRepository orderTableRepository;
    private ToBeOrderService orderService;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        orderTableRepository = new InMemoryOrderTableRepository();
        orderService = new ToBeOrderService(orderRepository, menuRepository, orderTableRepository);
    }


    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, purgomalumClient, menuProduct(purgomalumClient))).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final ToBeOrder expected = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final ToBeOrder actual = orderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getStatus()).isEqualTo(ToBeOrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<ToBeOrderLineItem> orderLineItems) {
        final ToBeOrder expected = createOrderRequest( orderLineItems);
        assertThatThrownBy(() -> orderService.create(expected))
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
        final UUID menuId = menuRepository.save(menu(19_000L, true,purgomalumClient, menuProduct(purgomalumClient))).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final ToBeOrder expected = createOrderRequest(
             orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertDoesNotThrow(() -> orderService.create(expected));
    }


    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, purgomalumClient, menuProduct(purgomalumClient))).getId();
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final ToBeOrder expected = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, false, purgomalumClient,menuProduct(purgomalumClient))).getId();
        final ToBeOrder expected = createOrderRequest( createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, purgomalumClient, menuProduct(purgomalumClient))).getId();
        final ToBeOrder expected = createOrderRequest( createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = orderRepository.save(order(ToBeOrderStatus.WAITING, orderTable(true, 4),purgomalumClient)).getId();
        final ToBeOrder actual = orderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(ToBeOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = ToBeOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final ToBeOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4),purgomalumClient)).getId();
        assertThatThrownBy(() -> orderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = orderRepository.save(order(ToBeOrderStatus.ACCEPTED, orderTable(true, 4),purgomalumClient)).getId();
        final ToBeOrder actual = orderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(ToBeOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = ToBeOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final ToBeOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4),purgomalumClient)).getId();
        assertThatThrownBy(() -> orderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = ToBeOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final ToBeOrderStatus status) {
        final UUID orderId = orderRepository.save(order(status, orderTable(true, 4),purgomalumClient)).getId();
        assertThatThrownBy(() -> orderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }



    private ToBeOrder createOrderRequest( final ToBeOrderLineItem... orderLineItems) {
        return createOrderRequest( Arrays.asList(orderLineItems));
    }

    private ToBeOrder createOrderRequest( final List<ToBeOrderLineItem> orderLineItems) {
        return createOrderRequest(UUID.randomUUID(), (ToBeOrderLineItem) Arrays.asList(orderLineItems));

    }

    private ToBeOrder createOrderRequest(
        final UUID orderTableId,
        final ToBeOrderLineItem... orderLineItems
    ) {
        final ToBeOrder order = new ToBeOrder(ToBeOrderStatus.WAITING,LocalDateTime.of(2020, 1, 1, 12, 0),Arrays.asList(orderLineItems));
        ReflectionTestUtils.setField(order,"tableId",orderTableId);


        return order;
    }

    private static ToBeOrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        final ToBeOrderLineItem orderLineItem = new ToBeOrderLineItem();
        ReflectionTestUtils.setField(orderLineItem,"seq",new Random().nextLong());
        ReflectionTestUtils.setField(orderLineItem,"menuId",menuId);
        ReflectionTestUtils.setField(orderLineItem,"price",BigDecimal.valueOf(price));
        ReflectionTestUtils.setField(orderLineItem,"quantity",quantity);

        return orderLineItem;
    }
}
