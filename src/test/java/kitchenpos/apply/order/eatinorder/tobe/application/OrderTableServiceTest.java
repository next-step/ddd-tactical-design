package kitchenpos.apply.order.eatinorder.tobe.application;

import kitchenpos.apply.order.eatinorder.tobe.domain.InMemoryEatInOrderRepository;
import kitchenpos.apply.order.eatinorder.tobe.domain.InMemoryOrderTableRepository;
import kitchenpos.apply.order.eatinorders.tobe.application.OrderTableService;
import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrderStatus;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTable;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.OrderTableRequest;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.OrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.apply.fixture.TobeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableServiceTest {
    private OrderTableRepository orderTableRepository;
    private InMemoryEatInOrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryEatInOrderRepository();
        orderTableService = new OrderTableService(orderTableRepository, orderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTableRequest expected = orderTableRequest("1번");
        final OrderTableResponse actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final OrderTableRequest expected = orderTableRequest(name);
        assertThatThrownBy(() -> orderTableService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(false, 0)).getId());
        final OrderTableResponse actual = orderTableService.sit(orderTableId);
        assertThat(actual.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(true, 4)).getId());
        final OrderTableResponse actual = orderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderTableId = UUID.fromString(orderTable.getId());
        orderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(true, 0)).getId());
        final OrderTableRequest expected = orderTableRequest(true, 4);
        final OrderTableResponse actual = orderTableService.changeNumberOfGuests(orderTableId, expected);
        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(true, 0)).getId());
        final OrderTableRequest expected = orderTableRequest(numberOfGuests);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = UUID.fromString(orderTableRepository.save(orderTable(false, 0)).getId());
        final OrderTableRequest expected = orderTableRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(NoSuchElementException.class);
    }
}
