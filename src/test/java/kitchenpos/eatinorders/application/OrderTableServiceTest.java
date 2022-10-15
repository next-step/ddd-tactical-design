package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.OrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.OrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.OrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ui.request.OrderTableCreateRequest;
import kitchenpos.eatinorders.ui.response.OrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTableServiceTest {
    private OrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableService = new OrderTableService(orderTableRepository, orderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        OrderTableCreateRequest request = new OrderTableCreateRequest("1번");
        OrderTableResponse response = orderTableService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo("1번"),
            () -> assertThat(response.getNumberOfGuests()).isZero(),
            () -> assertThat(response.isOccupied()).isFalse()
        );
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final OrderTableResponse response = orderTableService.sit(orderTableId);
        assertThat(response.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final OrderTableResponse response = orderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(response.getNumberOfGuests()).isZero(),
            () -> assertThat(response.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderTableId = orderTable.getId();
        orderRepository.save(eatInOrder(OrderStatus.ACCEPTED, orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final OrderTableChangeNumberOfGuestsRequest request = new OrderTableChangeNumberOfGuestsRequest(4);
        final OrderTableResponse response = orderTableService.changeNumberOfGuests(orderTableId, request);
        assertThat(response.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final OrderTableChangeNumberOfGuestsRequest request = new OrderTableChangeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final OrderTableChangeNumberOfGuestsRequest request = new OrderTableChangeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, request))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTableResponse> responses = orderTableService.findAll();
        assertThat(responses).hasSize(1);
    }
}
