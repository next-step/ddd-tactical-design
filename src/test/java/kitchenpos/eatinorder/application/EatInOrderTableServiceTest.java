package kitchenpos.eatinorder.application;

import static kitchenpos.Fixtures.eatInOrder;
import static kitchenpos.Fixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorder.InMemoryEatInOrderRepository;
import kitchenpos.eatinorder.InMemoryEatInOrderTableRepository;
import kitchenpos.eatinorder.domain.EatInOrderRepository;
import kitchenpos.eatinorder.domain.EatInOrderStatus;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import kitchenpos.eatinorder.domain.EatInOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EatInOrderTableServiceTest {
    private EatInOrderTableRepository eatInOrderTableRepository;
    private EatInOrderRepository eatInOrderRepository;
    private EatInOrderTableService eatInOrderTableService;

    @BeforeEach
    void setUp() {
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        eatInOrderTableService = new EatInOrderTableService(
            eatInOrderTableRepository,
            eatInOrderRepository
        );
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final EatInOrderTable expected = createOrderTableRequest("1번");
        final EatInOrderTable actual = eatInOrderTableService.create(expected);
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
        final EatInOrderTable expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> eatInOrderTableService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = eatInOrderTableRepository.save(
            eatInOrderTable(false, 0)
        ).getId();
        final EatInOrderTable actual = eatInOrderTableService.sit(orderTableId);
        assertThat(actual.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = eatInOrderTableRepository.save(
            eatInOrderTable(true, 4)
        ).getId();
        final EatInOrderTable actual = eatInOrderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(
            eatInOrderTable(true, 4)
        );
        final UUID orderTableId = eatInOrderTable.getId();
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, eatInOrderTable));
        assertThatThrownBy(() -> eatInOrderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = eatInOrderTableRepository.save(
            eatInOrderTable(true, 0)
        ).getId();
        final EatInOrderTable expected = changeNumberOfGuestsRequest(4);
        final EatInOrderTable actual = eatInOrderTableService.changeNumberOfGuests(
            orderTableId,
            expected
        );
        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = eatInOrderTableRepository.save(
            eatInOrderTable(true, 0)
        ).getId();
        final EatInOrderTable expected = changeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(
            () -> eatInOrderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = eatInOrderTableRepository.save(
            eatInOrderTable(false, 0)
        ).getId();
        final EatInOrderTable expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() ->
            eatInOrderTableService.changeNumberOfGuests(orderTableId, expected)
        )
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        eatInOrderTableRepository.save(eatInOrderTable());
        final List<EatInOrderTable> actual = eatInOrderTableService.findAll();
        assertThat(actual).hasSize(1);
    }

    private EatInOrderTable createOrderTableRequest(final String name) {
        final EatInOrderTable eatInOrderTable = new EatInOrderTable();
        eatInOrderTable.setName(name);
        return eatInOrderTable;
    }

    private EatInOrderTable changeNumberOfGuestsRequest(final int numberOfGuests) {
        final EatInOrderTable eatInOrderTable = new EatInOrderTable();
        eatInOrderTable.setNumberOfGuests(numberOfGuests);
        return eatInOrderTable;
    }
}
