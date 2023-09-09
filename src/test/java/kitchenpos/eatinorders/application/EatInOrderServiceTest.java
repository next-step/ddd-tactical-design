package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.OrderFixture.createEatInOrderRequestBuilder;
import static kitchenpos.eatinorders.OrderTableFixture.orderTable;
import static kitchenpos.eatinorders.domain.OrderStatus.ACCEPTED;
import static kitchenpos.eatinorders.domain.OrderType.EAT_IN;
import static kitchenpos.menus.MenuFixture.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.eatinorders.OrderFixture;
import kitchenpos.eatinorders.OrderTableFixture;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EatInOrderServiceTest {

    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private OrderService orderService;
    private Menu menu;
    private OrderTable orderTable;
    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        orderService = new OrderService(orderRepository, menuRepository, orderTableRepository, kitchenridersClient);
        menu = menuRepository.save(menu());
        orderTable = orderTableRepository.save(orderTable());
    }

    @DisplayName("매장주문을 등록할수 있다")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 1, 2, 3, 4, 5})
    void test1(long quantity) {
        //given
        Order createRequest = createEatInOrderRequestBuilder()
            .menu(menu, quantity, menu.getPrice())
            .orderTable(orderTable.getId())
            .type(EAT_IN)
            .build();

        //when
        Order result = orderService.create(createRequest);

        //then
        assertThat(result.getId()).isNotNull();
    }

    @DisplayName("저장된 메뉴의 가격과 주문한 메뉴의 가격이 다를수 없다")
    @Test
    void test2() {
        //given
        Order createRequest = OrderFixture.createEatInOrderRequestBuilder()
            .menu(menu, 1L, menu.getPrice().add(BigDecimal.ONE))
            .orderTable(orderTable.getId())
            .type(EAT_IN)
            .build();

        //when && then
        assertThatThrownBy(
            () -> orderService.create(createRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("매장식사 주문은 사용중인 테이블에서만 주문할수 있다.")
    @Test
    void test3() {
        //given
        OrderTable vacantTable = orderTableRepository.save(orderTable(false));
        Order createRequest = OrderFixture.createEatInOrderRequestBuilder()
            .menu(menu, 1L, menu.getPrice())
            .orderTable(vacantTable.getId())
            .type(OrderType.EAT_IN)
            .build();

        //when && then
        assertThatThrownBy(
            () -> orderService.create(createRequest)
        ).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장식사 주문 접수시 '접수 완료'상태로 변경되어야 한다")
    @Test
    void test4() {
        //given
        Order order = orderService.create(
            OrderFixture.createEatInOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .orderTable(orderTable.getId())
                .type(OrderType.EAT_IN)
                .build()
        );
        //when
        Order result = orderService.accept(order.getId());

        //then
        assertThat(result.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("매장 식사 주문 서빙시 '서빙중' 상태로 변경되어야 한다")
    @Test
    void test5() {
        //given
        Order order = orderService.create(
            OrderFixture.createEatInOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .orderTable(orderTable.getId())
                .type(OrderType.EAT_IN)
                .build()
        );
        orderService.accept(order.getId());

        //when
        Order result = orderService.serve(order.getId());

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("매장 식사 주문을 완료처리 할수 있다. 처리 후 테이블 내 오더가 모두 완료시 테이블이 초기화되어야 한다")
    @Test
    void test6() {
        //given
        Order order = orderService.create(
            OrderFixture.createEatInOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .orderTable(orderTable.getId())
                .type(OrderType.EAT_IN)
                .build()
        );
        orderService.accept(order.getId());
        orderService.serve(order.getId());

        //when
        Order result = orderService.complete(order.getId());
        orderTable = orderTableRepository.findAll().get(0);

        //then
        assertAll(
            () -> assertThat(orderTable.isOccupied()).isFalse(),
            () -> assertThat(result.getStatus()).isEqualTo(OrderStatus.COMPLETED)
        );

    }

    @DisplayName("매장 식사 주문을 완료처리 할수 있다. 처리 후 테이블 내 완료되지 않은 오더가 존재시 테이블은 초기화되면 안된다")
    @Test
    void test7() {
        //given
        Order completedOrder = orderService.create(
            OrderFixture.createEatInOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .orderTable(orderTable.getId())
                .type(OrderType.EAT_IN)
                .build()
        );
        Order notCompletedOrder = orderService.create(
            OrderFixture.createEatInOrderRequestBuilder()
                .menu(menu, 1L, menu.getPrice())
                .orderTable(orderTable.getId())
                .type(OrderType.EAT_IN)
                .build()
        );
        orderService.accept(completedOrder.getId());
        orderService.serve(completedOrder.getId());

        //when
        Order result = orderService.complete(completedOrder.getId());
        orderTable = orderTableRepository.findAll().get(0);

        //then
        assertAll(
            () -> assertThat(orderTable.isOccupied()).isTrue(),
            () -> assertThat(result.getStatus()).isEqualTo(OrderStatus.COMPLETED)
        );

    }
}
