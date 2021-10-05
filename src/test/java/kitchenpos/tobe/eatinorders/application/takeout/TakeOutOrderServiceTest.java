package kitchenpos.tobe.eatinorders.application.takeout;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.HIDE_MENU_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.createOrderLineItemRequest;
import static kitchenpos.tobe.eatinorders.application.Fixtures.menu;
import static kitchenpos.tobe.eatinorders.application.Fixtures.takeOutOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.application.TakeOutOrderService;
import kitchenpos.eatinorders.tobe.application.exceptions.OrderStatusException;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrder;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrderStatus;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import kitchenpos.eatinorders.tobe.domain.repository.TakeOutOrderRepository;
import kitchenpos.eatinorders.tobe.infra.exception.CustomDomainExceptionHandler;
import kitchenpos.tobe.eatinorders.application.InMemoryMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class TakeOutOrderServiceTest {

    private TakeOutOrderRepository takeOutOrderRepository;
    private MenuRepository menuRepository;
    private TakeOutOrderService takeOutOrderService;

    @BeforeEach
    void setUp() {
        takeOutOrderRepository = new InMemoryTakeOutOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        takeOutOrderService = new TakeOutOrderService(takeOutOrderRepository, menuRepository, new CustomDomainExceptionHandler());
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createTakeOutOrder() {
        final UUID menuId = menu().getId();
        final TakeOutOrder expected = createOrderRequest(createOrderLineItemRequest(menuId, 19_000L, 3L));
        final TakeOutOrder actual = takeOutOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(TakeOutOrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final TakeOutOrder expected = createOrderRequest(orderLineItems);
        assertThatThrownBy(() -> takeOutOrderService.create(expected))
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
        final TakeOutOrder expected = createOrderRequest(createOrderLineItemRequest(HIDE_MENU_ID, 19_000L, 3L));
        assertThatThrownBy(() -> takeOutOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menu().getId();
        final TakeOutOrder expected = createOrderRequest(createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> takeOutOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = takeOutOrderRepository.save(takeOutOrder(TakeOutOrderStatus.WAITING)).getId();
        final TakeOutOrder actual = takeOutOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(TakeOutOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = TakeOutOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final TakeOutOrderStatus status) {
        final UUID orderId = takeOutOrderRepository.save(takeOutOrder(status)).getId();
        assertThatThrownBy(() -> takeOutOrderService.accept(orderId))
                .isInstanceOf(OrderStatusException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = takeOutOrderRepository.save(takeOutOrder(TakeOutOrderStatus.ACCEPTED)).getId();
        final TakeOutOrder actual = takeOutOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(TakeOutOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = TakeOutOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final TakeOutOrderStatus status) {
        final UUID orderId = takeOutOrderRepository.save(takeOutOrder(status)).getId();
        assertThatThrownBy(() -> takeOutOrderService.serve(orderId))
                .isInstanceOf(OrderStatusException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final TakeOutOrder expected = takeOutOrderRepository.save(takeOutOrder(TakeOutOrderStatus.SERVED));
        final TakeOutOrder actual = takeOutOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(TakeOutOrderStatus.COMPLETED);
    }

    @DisplayName("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = TakeOutOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndTakeOutOrder(final TakeOutOrderStatus status) {
        final UUID orderId = takeOutOrderRepository.save(takeOutOrder(status)).getId();
        assertThatThrownBy(() -> takeOutOrderService.complete(orderId))
                .isInstanceOf(OrderStatusException.class);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        takeOutOrderRepository.save(takeOutOrder(TakeOutOrderStatus.SERVED));
        takeOutOrderRepository.save(takeOutOrder(TakeOutOrderStatus.WAITING));
        final List<TakeOutOrder> actual = takeOutOrderRepository.findAll();
        assertThat(actual).hasSize(2);
    }

    private TakeOutOrder createOrderRequest(
            final OrderLineItem... orderLineItems
    ) {
        final TakeOutOrder order = new TakeOutOrder(Arrays.asList(orderLineItems));
        return order;
    }

    private TakeOutOrder createOrderRequest(
            final List<OrderLineItem> orderLineItems
    ) {
        final TakeOutOrder order = new TakeOutOrder(orderLineItems);
        return order;
    }



}
