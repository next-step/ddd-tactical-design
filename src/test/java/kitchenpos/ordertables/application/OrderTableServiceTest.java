package kitchenpos.ordertables.application;

import kitchenpos.eatinorders.application.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.ordertables.application.service.DefaultEatInOrderStatusLoader;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.dto.OrderTableRequest;
import kitchenpos.ordertables.exception.NumberOfGuestException;
import kitchenpos.ordertables.exception.OrderTableException;
import kitchenpos.ordertables.exception.OrderTableNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static kitchenpos.eatinorders.fixture.EatInOrderFixture.order;
import static kitchenpos.ordertables.fixture.OrderTableFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 테이블")
class OrderTableServiceTest {
    private kitchenpos.ordertables.domain.OrderTableRepository orderTableRepository;
    private EatInOrderRepository eatInOrderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        eatInOrderRepository = new InMemoryEatInOrderRepository();

        orderTableService = new OrderTableService(
                orderTableRepository,
                new DefaultEatInOrderStatusLoader(eatInOrderRepository)
        );
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTableRequest expected = createOrderTableRequest("1번");
        final OrderTable actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getNameValue()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getNumberOfGuestValue()).isZero(),
                () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final OrderTableRequest expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> orderTableService.create(expected))
                .isInstanceOf(OrderTableNameException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final OrderTableId orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final OrderTable actual = orderTableService.sit(orderTableId);
        assertThat(actual.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final OrderTableId orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final OrderTable actual = orderTableService.clear(orderTableId);
        assertAll(
                () -> assertThat(actual.getNumberOfGuestValue()).isZero(),
                () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final OrderTableId orderTableId = orderTable.getId();
        eatInOrderRepository.save(order(EatInOrderStatus.ACCEPTED, orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
                .isInstanceOf(OrderTableException.class);
    }

    @DisplayName("방문한 손님 수 변경")
    @Nested
    class changeNumberOfGuests {

        @DisplayName("방문한 손님 수를 변경할 수 있다.")
        @Test
        void changeNumberOfGuests() {
            final OrderTableId orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
            final OrderTable actual = orderTableService.changeNumberOfGuests(orderTableId, 4);
            assertThat(actual.getNumberOfGuestValue()).isEqualTo(4);
        }

        @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
        @ValueSource(ints = -1)
        @ParameterizedTest
        void changeNumberOfGuests(final int numberOfGuests) {
            final OrderTableId orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
            assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, numberOfGuests))
                    .isInstanceOf(NumberOfGuestException.class);
        }

        @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
        @Test
        void changeNumberOfGuestsInEmptyTable() {
            final OrderTableId orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();

            assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, 4))
                    .isInstanceOf(OrderTableException.class);
        }

    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTable> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }

    private OrderTableRequest createOrderTableRequest(String name) {
        return new OrderTableRequest(
                name,
                0,
                false
        );
    }

}
