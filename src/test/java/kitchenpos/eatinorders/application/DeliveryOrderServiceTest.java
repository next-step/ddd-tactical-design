package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.OrderFixture.createDeliveryOrderRequestBuilder;
import static kitchenpos.eatinorders.domain.OrderStatus.ACCEPTED;
import static kitchenpos.menus.MenuFixture.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.eatinorders.OrderFixture;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryOrderServiceTest {

    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private OrderService orderService;
    private Menu menu;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        orderService = new OrderService(orderRepository, menuRepository, orderTableRepository, kitchenridersClient);
        menu = menuRepository.save(menu());
    }

    @DisplayName("배달주문을 등록할수 있다. 주문이 되면 ID가 설정되고, '대기중' 상태가 된다")
    @Test
    void test1() {
        //given
        Order createRequest = createDeliveryOrderRequestBuilder()
            .deliveryAddress("배달 주소")
            .menu(menu, 1L, menu.getPrice())
            .type(OrderType.DELIVERY)
            .build();

        //when
        Order result = orderService.create(createRequest);

        //then
        assertAll(
            () -> assertThat(result.getId()).isNotNull(),
            () -> assertThat(result.getStatus()).isEqualTo(OrderStatus.WAITING)
        );
    }

    @DisplayName("저장된 메뉴의 가격과 주문한 메뉴의 가격이 다를수 없다")
    @Test
    void test2() {
        //given
        Order createRequest = OrderFixture.createDeliveryOrderRequestBuilder()
            .menu(menu, 1L, menu.getPrice().add(BigDecimal.ONE))
            .deliveryAddress("배달 주소")
            .type(OrderType.DELIVERY)
            .build();

        //when && then
        assertThatThrownBy(
            () -> orderService.create(createRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("'배달주문'은 메뉴 별로 0개 이상 주문해야 한다")
    @Test
    void test3() {
        //given
        long quantity = -1L;
        Order createRequest = OrderFixture.createDeliveryOrderRequestBuilder()
            .menu(menu, quantity, menu.getPrice())
            .type(OrderType.DELIVERY)
            .deliveryAddress("address")
            .build();

        //when && then
        assertThatThrownBy(
            () -> orderService.create(createRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달주문은 배달주소가 반드시 있어야 한다")
    @Test
    void test4() {
        //given
        Order createRequest = OrderFixture.createDeliveryOrderRequestBuilder()
            .menu(menu, 1L, menu.getPrice())
            .type(OrderType.DELIVERY)
            .build();

        //when && then
        assertThatThrownBy(
            () -> orderService.create(createRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주문 접수시 '접수 완료'상태로 변경되어야 한다")
    @Test
    void test5() {
        //given
        Order order = orderService.create(
            OrderFixture.createDeliveryOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .deliveryAddress("address")
                .type(OrderType.DELIVERY)
                .build()
        );
        //when
        Order result = orderService.accept(order.getId());

        //then
        assertThat(result.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("배달 주문 서빙시 '서빙중' 상태로 변경되어야 한다")
    @Test
    void test6() {
        //given
        Order order = orderService.create(
            OrderFixture.createDeliveryOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .deliveryAddress("address")
                .type(OrderType.DELIVERY)
                .build()
        );
        orderService.accept(order.getId());

        //when
        Order result = orderService.serve(order.getId());

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("배달 주문 시작시 '배달 중' 상태로 변경되어야 한다")
    @Test
    void test7() {
        //given

        Order order = orderService.create(
            OrderFixture.createDeliveryOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .deliveryAddress("address")
                .type(OrderType.DELIVERY)
                .build()
        );
        orderService.accept(order.getId());
        orderService.serve(order.getId());

        //when
        Order result = orderService.startDelivery(order.getId());

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @DisplayName("배달 주문 완료시 '배달 완료' 상태로 변경되어야 한다")
    @Test
    void test8() {
        //given
        Order order = orderService.create(
            OrderFixture.createDeliveryOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .deliveryAddress("address")
                .type(OrderType.DELIVERY)
                .build()
        );
        orderService.accept(order.getId());
        orderService.serve(order.getId());
        orderService.startDelivery(order.getId());

        //when
        Order result = orderService.completeDelivery(order.getId());

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }


}
