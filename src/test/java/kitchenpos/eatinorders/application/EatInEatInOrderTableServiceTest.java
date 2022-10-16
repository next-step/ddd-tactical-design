package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.EatInOrderFixtures;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.EatInOrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ui.request.EatInOrderTableCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EatInEatInOrderTableServiceTest {
    private EatInOrderTableRepository eatInOrderTableRepository;
    private EatInOrderRepository eatInOrderRepository;
    private EatInOrderTableService eatInOrderTableService;

    @BeforeEach
    void setUp() {
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        eatInOrderTableService = new EatInOrderTableService(eatInOrderTableRepository, eatInOrderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        EatInOrderTableCreateRequest request = new EatInOrderTableCreateRequest("1번");
        EatInOrderTableResponse response = eatInOrderTableService.create(request);
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
        final UUID orderTableId = eatInOrderTableRepository.save(EatInOrderFixtures.eatInOrderTable(false, 0)).getId();
        final EatInOrderTableResponse response = eatInOrderTableService.sit(orderTableId);
        assertThat(response.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = eatInOrderTableRepository.save(EatInOrderFixtures.eatInOrderTable(true, 4)).getId();
        final EatInOrderTableResponse response = eatInOrderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(response.getNumberOfGuests()).isZero(),
            () -> assertThat(response.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(EatInOrderFixtures.eatInOrderTable(true, 4));
        final UUID orderTableId = eatInOrderTable.getId();
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, eatInOrderTable));
        assertThatThrownBy(() -> eatInOrderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = eatInOrderTableRepository.save(EatInOrderFixtures.eatInOrderTable(true, 0)).getId();
        final EatInOrderTableChangeNumberOfGuestsRequest request = new EatInOrderTableChangeNumberOfGuestsRequest(4);
        final EatInOrderTableResponse response = eatInOrderTableService.changeNumberOfGuests(orderTableId, request);
        assertThat(response.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = eatInOrderTableRepository.save(EatInOrderFixtures.eatInOrderTable(true, 0)).getId();
        final EatInOrderTableChangeNumberOfGuestsRequest request = new EatInOrderTableChangeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> eatInOrderTableService.changeNumberOfGuests(orderTableId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = eatInOrderTableRepository.save(EatInOrderFixtures.eatInOrderTable(false, 0)).getId();
        final EatInOrderTableChangeNumberOfGuestsRequest request = new EatInOrderTableChangeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> eatInOrderTableService.changeNumberOfGuests(orderTableId, request))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        eatInOrderTableRepository.save(eatInOrderTable());
        final List<EatInOrderTableResponse> responses = eatInOrderTableService.findAll();
        assertThat(responses).hasSize(1);
    }
}
