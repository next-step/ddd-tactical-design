package kitchenpos.eatinordertables.application;

import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrder;
import static kitchenpos.eatinordertables.EatInOrderTableFixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.UUID;
import kitchenpos.eatinorders.application.EatInOrderCompletedCheckerImpl;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.domain.InMemoryEatInOrderRepository;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.domain.InMemoryEatInOrderTableRepository;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableCreateRequest;
import kitchenpos.eatinordertables.ui.response.EatInOrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EatInOrderTableCommandServiceTest {
    private EatInOrderTableRepository eatInOrderTableRepository;
    private EatInOrderRepository eatInOrderRepository;
    private EatInOrderTableCommandService eatInOrderTableCommandService;

    @BeforeEach
    void setUp() {
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        eatInOrderTableCommandService = new EatInOrderTableCommandService(eatInOrderTableRepository, new EatInOrderCompletedCheckerImpl(eatInOrderRepository));
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        EatInOrderTableCreateRequest request = new EatInOrderTableCreateRequest("1번");
        EatInOrderTableResponse response = eatInOrderTableCommandService.create(request);
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
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(false, 0)).getId();
        final EatInOrderTableResponse response = eatInOrderTableCommandService.sit(orderTableId);
        assertThat(response.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4)).getId();
        final EatInOrderTableResponse response = eatInOrderTableCommandService.clear(orderTableId);
        assertAll(
            () -> assertThat(response.getNumberOfGuests()).isZero(),
            () -> assertThat(response.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        final UUID orderTableId = eatInOrderTable.getId();
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, orderTableId));
        assertThatThrownBy(() -> eatInOrderTableCommandService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("완료되지 않은 주문이 존재해서 테이블을 비울 수 없습니다.");
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 0)).getId();
        final EatInOrderTableChangeNumberOfGuestsRequest request = new EatInOrderTableChangeNumberOfGuestsRequest(4);
        final EatInOrderTableResponse response = eatInOrderTableCommandService.changeNumberOfGuests(orderTableId, request);
        assertThat(response.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 0)).getId();
        final EatInOrderTableChangeNumberOfGuestsRequest request = new EatInOrderTableChangeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> eatInOrderTableCommandService.changeNumberOfGuests(orderTableId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(false, 0)).getId();
        final EatInOrderTableChangeNumberOfGuestsRequest request = new EatInOrderTableChangeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> eatInOrderTableCommandService.changeNumberOfGuests(orderTableId, request))
            .isInstanceOf(IllegalStateException.class);
    }
}
