package kitchenpos.eatinorders.application.tobe;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.application.tobe.exception.InvalidOrderTableStateException;
import kitchenpos.eatinorders.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.infra.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.common.*;
import kitchenpos.eatinorders.presentation.dto.OrderTableChangeNumberOfGuestsResponse;
import kitchenpos.eatinorders.presentation.dto.OrderTableClearResponse;
import kitchenpos.eatinorders.presentation.dto.OrderTableCreateResponse;
import kitchenpos.eatinorders.presentation.dto.OrderTableSitResponse;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableServiceTest {

    private OrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setup() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableService = new OrderTableService(orderTableRepository);
    }

    @DisplayName("주문 테이블을 생성한다")
    @Test
    void create() {
        OrderTableCreateResponse result = orderTableService.create("1번 테이블");

        assertAll(
                () -> assertThat(result.getName()).isEqualTo(new OrderTableName("1번 테이블")),
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(PositiveNumber.ZERO),
                () -> assertThat(result.isOccupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블에 손님이 앉는다")
    @Test
    void sit() {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 0, false));

        OrderTableSitResponse result = orderTableService.sit(table.getId());

        assertThat(result.isOccupied()).isTrue();
    }

    @DisplayName("주문 테이블을 정리한다")
    @Test
    void clear() {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), 1, new Price(25_000))
        );
        orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                OrderStatus.COMPLETED,
                orderLineItems,
                null,
                table.getId()
        ));

        OrderTableClearResponse result = orderTableService.clear(table.getId());

        assertThat(result.isOccupied()).isFalse();
        assertThat(result.getNumberOfGuests()).isEqualTo(PositiveNumber.ZERO);
    }
    
    @DisplayName("빈 주문 테이블의 손님의 수를 변경하면 예외가 발생한다")
    @Test
    void changeNumberOfGuestsByEmptyTable() {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 0, false));

        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(table.getId(), 3))
                .isInstanceOf(InvalidOrderTableStateException.class);
    }

    @DisplayName("주문 테이블에 배정된 손님의 수를 변경한다")
    @Test
    void changeNumberOfGuests() {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 5, true));

        OrderTableChangeNumberOfGuestsResponse result = orderTableService.changeNumberOfGuests(table.getId(), 3);

        assertThat(result.getNumberOfGuests()).isEqualTo(new PositiveNumber(3));
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
