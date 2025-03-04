package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.application.tobe.exception.InvalidOrderTableStateException;
import kitchenpos.eatinorders.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.infra.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.common.*;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClearOrderTableServiceTest {

    private OrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;
    private ClearOrderTableService clearOrderTableService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        clearOrderTableService = new ClearOrderTableService(orderRepository, orderTableRepository);
    }

    @DisplayName("주문 완료 처리 시, 미완료 주문이 있는 주문 테이블은 빈 주문 테이블이 되지 않는다")
    @EnumSource(value = OrderStatus.class, names = "COMPLETED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateOrderStatus(OrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 5, true));
        OrderEntity orderEntity = new OrderEntity(
                OrderType.EAT_IN,
                status,
                new OrderLineItems(
                        new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
                ),
                null,
                table.getId()
        );
        OrderEntity order = orderRepository.save(orderEntity);

        clearOrderTableService.clearOrderTable(order.id());

        assertThat(table.isOccupied()).isTrue();
        assertThat(table.getNumberOfGuests()).isEqualTo(new PositiveNumber(5));
    }

    @DisplayName("완료된 주문이 있는 주문 테이블을 정리하면 빈 주문 테이블로 만든다")
    @Test
    void clearByOrder() {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 5, true));
        OrderEntity orderEntity = new OrderEntity(
                OrderType.EAT_IN,
                OrderStatus.COMPLETED,
                new OrderLineItems(
                        new OrderLineItem(1L, MenuId.generate(), 1, new Price(10_000))
                ),
                null,
                table.getId()
        );
        OrderEntity order = orderRepository.save(orderEntity);

        clearOrderTableService.clearOrderTable(order.id());

        assertThat(table.isOccupied()).isFalse();
        assertThat(table.getNumberOfGuests()).isEqualTo(PositiveNumber.ZERO);
    }

    private OrderTable createOrderTable(String name, int numberOfGuests, boolean occupied) {
        return new OrderTable(
                OrderTableId.generate(),
                new OrderTableName(name),
                new PositiveNumber(numberOfGuests),
                occupied
        );
    }
}
