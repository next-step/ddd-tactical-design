package kitchenpos.tobe.eatinorders.application.delivery;

import static kitchenpos.tobe.eatinorders.application.Fixtures.INVALID_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.createOrderLineItemRequest;
import static kitchenpos.tobe.eatinorders.application.Fixtures.hideMenu;
import static kitchenpos.tobe.eatinorders.application.Fixtures.menu;
import static kitchenpos.tobe.eatinorders.application.Fixtures.deliveryOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.application.DeliveryOrderService;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrder;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.repository.DeliveryOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import kitchenpos.tobe.eatinorders.application.Fixtures;
import kitchenpos.tobe.eatinorders.application.InMemoryMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class DeliveryOrderServiceTest {

    private DeliveryOrderRepository deliveryOrderRepository;
    private MenuRepository menuRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private DeliveryOrderService deliveryOrderService;

    @BeforeEach
    void setUp() {
        deliveryOrderRepository = new InMemoryDeliveryOrderRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        menuRepository = new InMemoryMenuRepository();
        deliveryOrderService = new DeliveryOrderService(deliveryOrderRepository, menuRepository, kitchenridersClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final UUID menuId = menu().getId();
        final DeliveryOrder expected = createOrderRequest(
                "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        final DeliveryOrder actual = deliveryOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
                () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void create(final List<OrderLineItem> orderLineItems) {
        final DeliveryOrder expected = createOrderRequest("서울시 송파구 위례성대로 2", orderLineItems);
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutEatInOrder(final long quantity) {
        final UUID menuId = menu().getId();
        final DeliveryOrder expected = createOrderRequest(
                "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, quantity)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String deliveryAddress) {
        final UUID menuId = menu().getId();
        assertThatThrownBy(() -> createOrderRequest(
                deliveryAddress, createOrderLineItemRequest(menuId, 19_000L, 3L)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = hideMenu(19_000L, false).getId();
        final DeliveryOrder expected = createOrderRequest(
                "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menu().getId();
        final DeliveryOrder expected = createOrderRequest(
                "서울시 송파구 위례성대로 2", createOrderLineItemRequest(menuId, 16_000L, 3L)
        );
        assertThatThrownBy(() -> deliveryOrderService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID deliveryOrderId = deliveryOrderRepository.save(Fixtures.deliveryOrder("서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = deliveryOrderService.accept(deliveryOrderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final DeliveryOrderStatus status) {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(status,"서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> deliveryOrderService.accept(deliveryOrderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final UUID deliveryOrderId = deliveryOrderRepository.save(Fixtures.deliveryOrder("서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = deliveryOrderService.accept(deliveryOrderId);
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.ACCEPTED),
                () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(deliveryOrderId),
                () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울시 송파구 위례성대로 2")
        );
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(DeliveryOrderStatus.ACCEPTED,"서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = deliveryOrderService.serve(deliveryOrderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.SERVED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final DeliveryOrderStatus status) {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(status,"서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> deliveryOrderService.serve(deliveryOrderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달한다.")
    @Test
    void startDelivery() {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(DeliveryOrderStatus.SERVED,"서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = deliveryOrderService.startDelivery(deliveryOrderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERING);
    }

    @DisplayName("서빙된 주문만 배달할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void startDelivery(final DeliveryOrderStatus status) {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(status,"서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> deliveryOrderService.startDelivery(deliveryOrderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 배달 완료한다.")
    @Test
    void completeDelivery() {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(DeliveryOrderStatus.DELIVERING,"서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = deliveryOrderService.completeDelivery(deliveryOrderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.DELIVERED);
    }

    @DisplayName("배달 중인 주문만 배달 완료할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDelivery(final DeliveryOrderStatus status) {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(status,"서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> deliveryOrderService.completeDelivery(deliveryOrderId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(DeliveryOrderStatus.DELIVERED,"서울시 송파구 위례성대로 2")).getId();
        final DeliveryOrder actual = deliveryOrderService.complete(deliveryOrderId);
        assertThat(actual.getStatus()).isEqualTo(DeliveryOrderStatus.COMPLETED);
    }

    @DisplayName("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.")
    @EnumSource(value = DeliveryOrderStatus.class, names = "DELIVERED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeDeliveryOrder(final DeliveryOrderStatus status) {
        final UUID deliveryOrderId = deliveryOrderRepository.save(deliveryOrder(status,"서울시 송파구 위례성대로 2")).getId();
        assertThatThrownBy(() -> deliveryOrderService.complete(deliveryOrderId))
                .isInstanceOf(IllegalStateException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(createOrderLineItemRequest(INVALID_ID, 19_000L, 3L)))
        );
    }

    private DeliveryOrder createOrderRequest(
            final String deliveryAddress,
            final OrderLineItem... orderLineItems
    ) {
        final DeliveryOrder order = new DeliveryOrder(Arrays.asList(orderLineItems), deliveryAddress);
        return order;
    }

    private DeliveryOrder createOrderRequest(
            final String deliveryAddress,
            final List<OrderLineItem> orderLineItems
    ) {
        final DeliveryOrder order = new DeliveryOrder(orderLineItems, deliveryAddress);
        return order;
    }

}
