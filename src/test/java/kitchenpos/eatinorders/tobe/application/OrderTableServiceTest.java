package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.infra.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.EatInOrderFixtures.acceptedOrder;
import static kitchenpos.eatinorders.tobe.EatInOrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableServiceTest {
    private OrderTableRepository orderTableRepository;
    private EatInOrderRepository eatInOrderRepository;
    private OrderTableService sut;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        sut = new OrderTableService(orderTableRepository, eatInOrderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTableRequest expected = createOrderTableRequest("1번");
        final OrderTable actual = sut.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(new OrderTableName(expected.getName())),
            () -> assertThat(actual.getNumberOfGuests()).isEqualTo(new NumberOfGuests(3)),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final OrderTableRequest expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> sut.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(orderTable(0, false)).getId();
        final OrderTable actual = sut.sit(orderTableId);
        assertThat(actual.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(orderTable(4, true)).getId();
        final OrderTable actual = sut.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.getNumberOfGuests()).isEqualTo(new NumberOfGuests(0)),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final UUID orderTableId = orderTable.getId();
        eatInOrderRepository.save(acceptedOrder(orderTable));
        assertThatThrownBy(() -> sut.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(orderTable(0, true)).getId();
        final OrderTableRequest expected = changeNumberOfGuestsRequest(4);
        final OrderTable actual = sut.changeNumberOfGuests(orderTableId, expected);
        assertThat(actual.getNumberOfGuests()).isEqualTo(new NumberOfGuests(4));
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(0, true)).getId();
        final OrderTableRequest expected = changeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> sut.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(0, false)).getId();
        final OrderTableRequest expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> sut.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTable> actual = sut.findAll();
        assertThat(actual).hasSize(1);
    }

    private OrderTableRequest createOrderTableRequest(final String name) {
        return new OrderTableRequest(name, 3);
    }

    private OrderTableRequest changeNumberOfGuestsRequest(final int numberOfGuests) {
        return new OrderTableRequest("테이블", numberOfGuests);
    }
}
