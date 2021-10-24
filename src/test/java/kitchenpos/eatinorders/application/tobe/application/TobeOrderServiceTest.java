package kitchenpos.eatinorders.application.tobe.application;

import kitchenpos.eatinorders.application.FakeKitchenridersClient;
import kitchenpos.eatinorders.application.tobe.OrderFixtures;
import kitchenpos.eatinorders.application.tobe.infra.FakeOrderMenuAdaptor;
import kitchenpos.eatinorders.application.tobe.infra.TobeInMemoryOrderRepository;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.tobe.application.TobeOrderService;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.ui.OrderForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeOrderServiceTest extends OrderFixtures {
    private TobeOrderRepository orderRepository;
    private FakeOrderMenuAdaptor orderMenuAdaptor;
    private OrderLineFactory orderLineFactory;
    private FakeKitchenridersClient kitchenridersClient;
    private TobeOrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new TobeInMemoryOrderRepository();
        orderMenuAdaptor = new FakeOrderMenuAdaptor();
        orderLineFactory = new OrderLineFactory();
        kitchenridersClient = new FakeKitchenridersClient();
        orderService = new TobeOrderService(orderRepository, orderMenuAdaptor, orderLineFactory, kitchenridersClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void createDeliveryOrder() {
        final OrderForm expected = createOrderForm();
        final OrderForm actual = orderService.create(expected);

        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(3),
            () -> assertThat(actual.getDeliveryAddress()).isEqualTo(expected.getDeliveryAddress())
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final OrderForm expected = createOrderForm(OrderType.TAKEOUT);
        final OrderForm actual = orderService.create(expected);

        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(3)
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID orderTableId = createOrderTableForm().getId();
        final OrderForm expected = createOrderForm(OrderType.TAKEOUT);
        expected.setOrderTableId(orderTableId);
        final OrderForm actual = orderService.create(expected);

        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(3),
            () -> assertThat(actual.getOrderTableId()).isEqualTo(expected.getOrderTableId())
        );
    }


    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final OrderForm expected = OrderForm.of(createOrder());

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final Menu menu = createMenu(BigDecimal.valueOf(1000L));
        final OrderForm expected = createOrderForm(OrderType.EAT_IN, createOrderLineItems(createOrderLineItem(menu, 3L)));

        assertThatThrownBy(() -> orderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주문을 접수되면 배달 대행사를 호출한다.")
    @Test
    void acceptDeliveryOrder() {
        final OrderForm expected = orderService.create(createOrderForm(OrderType.DELIVERY));
        final OrderForm actual = orderService.accept(expected.getId());

        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED),
            () -> assertThat(kitchenridersClient.getOrderId()).isEqualTo(expected.getId()),
            () -> assertThat(kitchenridersClient.getDeliveryAddress()).isEqualTo("서울")
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderService.create(createOrderForm(OrderType.DELIVERY));
        orderService.create(createOrderForm(OrderType.DELIVERY));

        final List<OrderForm> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private OrderForm createOrderForm() {
        return createOrderForm(OrderType.EAT_IN);
    }

    private OrderForm createOrderForm(OrderType type) {
        List<TobeOrderLineItem> items = new ArrayList<>();
        items.add(createOrderLineItem(1L));
        items.add(createOrderLineItem(1L));
        items.add(createOrderLineItem(1L));

        TobeOrderLineItems orderLineItems = new TobeOrderLineItems(items);

        orderMenuAdaptor.save(items.stream()
                                .map(TobeOrderLineItem::getMenu)
                                .collect(Collectors.toList()));

        return OrderForm.of(createOrder(type, orderLineItems));
    }

    private OrderForm createOrderForm(OrderType type, TobeOrderLineItems orderLineItems) {
        orderMenuAdaptor.save(orderLineItems.getOrderLineItems().stream()
                .map(TobeOrderLineItem::getMenu)
                .collect(Collectors.toList()));

        return OrderForm.of(createOrder(type, orderLineItems));
    }
}
