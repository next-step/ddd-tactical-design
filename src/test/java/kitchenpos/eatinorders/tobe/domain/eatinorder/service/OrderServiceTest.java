package kitchenpos.eatinorders.tobe.domain.eatinorder.service;

import kitchenpos.eatinorders.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.Order;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static kitchenpos.eatinorders.TobeFixtures.orderForEmptyTable;
import static kitchenpos.eatinorders.TobeFixtures.orderForTable1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderTableRepository orderTableService;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(menuService, orderRepository, orderTableService);
    }

    @DisplayName("1 개 이상의 등록된 메뉴로 주문을 등록할 수 있다.")
    @Test
    void create() {
        final Order expected = orderForTable1();

        final Order actual = orderService.create(expected);

        assertOrder(expected, actual);
    }

    @DisplayName("빈 테이블에는 주문을 등록할 수 없다.")
    @Test
    void createFromEmptyTable() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> orderService.create(orderForEmptyTable()));
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void list() {
        final Order expected = orderForTable1();
        orderService.create(expected);

        List<Order> actual = orderService.list();
        assertThat(actual).contains(expected);
    }

    @DisplayName("주문 상태를 변경할 수 있다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = {"MEAL", "COOKING"})
    void changeOrderStatus(final OrderStatus orderStatus) {
        final Long orderId = orderService.create(orderForTable1()).getId();

        final Order actual = orderService.changeOrderStatus(orderId, new Order(orderStatus));

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(orderId),
                () -> assertThat(actual.getOrderStatus()).isEqualTo(orderStatus)
        );
    }

    @DisplayName("주문 상태가 계산 완료인 경우 변경할 수 없다.")
    @Test
    void changeOrderStatus() {
        final Long orderId = orderService.create(orderForTable1()).getId();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderService.changeOrderStatus(orderId, new Order(OrderStatus.COMPLETION)))
        ;
    }

    private void assertOrder(final Order expected, final Order actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getOrderTableId()).isEqualTo(expected.getOrderTableId()),
                () -> assertThat(actual.getOrderStatus()).isEqualTo(expected.getOrderStatus()),
                () -> assertThat(actual.getOrderLineItems())
                        .containsExactlyInAnyOrderElementsOf(expected.getOrderLineItems())
        );
    }
}
