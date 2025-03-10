package kitchenpos.eatinorders.tobe.application.restaurant;


import static kitchenpos.mock.fixture.RestaurantTableFixture.changeNumberOfGuestsRequest;
import static kitchenpos.mock.fixture.RestaurantTableFixture.createOrderTableRequest;
import static kitchenpos.mock.fixture.RestaurantTableFixture.eatInorder;
import static kitchenpos.mock.fixture.RestaurantTableFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableChangeRequest;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableCreateRequest;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableResponse;
import kitchenpos.mock.persistence.FakeEatInOrderRepository;
import kitchenpos.mock.persistence.FakeRestaurantTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TobeRestaurantTableServiceTest {

    private FakeRestaurantTableRepository orderTableRepository;
    private FakeEatInOrderRepository eatInOrderRepository;
    private TobeRestaurantTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new FakeRestaurantTableRepository();
        eatInOrderRepository = new FakeEatInOrderRepository();
        orderTableService = new TobeRestaurantTableService(orderTableRepository, eatInOrderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final RestaurantTableCreateRequest expected = createOrderTableRequest("1번");
        final RestaurantTableResponse actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id()).isNotNull(),
            () -> assertThat(actual.name()).isEqualTo(expected.name()),
            () -> assertThat(actual.numberOfGuests()).isZero(),
            () -> assertThat(actual.occupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final RestaurantTableCreateRequest expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> orderTableService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final RestaurantTableResponse actual = orderTableService.sit(orderTableId);
        assertThat(actual.occupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final RestaurantTableResponse actual = orderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.numberOfGuests()).isZero(),
            () -> assertThat(actual.occupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final RestaurantTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderTableId = orderTable.getId();
        eatInOrderRepository.save(eatInorder(EatInOrderStatus.ACCEPTED, orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final RestaurantTableChangeRequest expected = changeNumberOfGuestsRequest(4);
        final RestaurantTableResponse actual = orderTableService.changeNumberOfGuests(orderTableId,
            expected);
        assertThat(actual.numberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final RestaurantTableChangeRequest expected = changeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final RestaurantTableChangeRequest expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable(true, 1));
        final List<RestaurantTable> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }
}
