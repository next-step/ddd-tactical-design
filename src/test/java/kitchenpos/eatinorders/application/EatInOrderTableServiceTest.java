package kitchenpos.eatinorders.application;

import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTableServiceTest {
  private EatInOrderTableRepository orderTableRepository;
  private EatInOrderRepository orderRepository;
  private EatInOrderTableService orderTableService;

  @BeforeEach
  void setUp() {
    orderTableRepository = new InMemoryEatInOrderTableRepository();
    orderRepository = new InMemoryEatInOrderRepository();
    orderTableService = new EatInOrderTableService(orderTableRepository, orderRepository);
  }

  @DisplayName("주문 테이블을 등록할 수 있다.")
  @Test
  void create() {
    final EatInOrderTable expected = createOrderTableRequest("1번");
    final EatInOrderTable actual = orderTableService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
        () -> assertThat(actual.getNumberOfGuests()).isZero(),
        () -> assertThat(actual.isOccupied()).isFalse());
  }

  @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create(final String name) {
    final EatInOrderTable expected = createOrderTableRequest(name);
    assertThatThrownBy(() -> orderTableService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("빈 테이블을 해지할 수 있다.")
  @Test
  void sit() {
    final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
    final EatInOrderTable actual = orderTableService.sit(orderTableId);
    assertThat(actual.isOccupied()).isTrue();
  }

  @DisplayName("빈 테이블로 설정할 수 있다.")
  @Test
  void clear() {
    final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
    final EatInOrderTable actual = orderTableService.clear(orderTableId);
    assertAll(
        () -> assertThat(actual.getNumberOfGuests()).isZero(),
        () -> assertThat(actual.isOccupied()).isFalse());
  }

  @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
  @Test
  void clearWithUncompletedOrders() {
    final EatInOrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
    final UUID orderTableId = orderTable.getId();
    orderRepository.save(order(EatInOrderStatus.ACCEPTED, orderTable));
    assertThatThrownBy(() -> orderTableService.clear(orderTableId))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("방문한 손님 수를 변경할 수 있다.")
  @Test
  void changeNumberOfGuests() {
    final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
    final EatInOrderTable expected = changeNumberOfGuestsRequest(4);
    final EatInOrderTable actual = orderTableService.changeNumberOfGuests(orderTableId, expected);
    assertThat(actual.getNumberOfGuests()).isEqualTo(4);
  }

  @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
  @ValueSource(ints = -1)
  @ParameterizedTest
  void changeNumberOfGuests(final int numberOfGuests) {
    final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
    final EatInOrderTable expected = changeNumberOfGuestsRequest(numberOfGuests);
    assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
  @Test
  void changeNumberOfGuestsInEmptyTable() {
    final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
    final EatInOrderTable expected = changeNumberOfGuestsRequest(4);
    assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    orderTableRepository.save(orderTable());
    final List<EatInOrderTable> actual = orderTableService.findAll();
    assertThat(actual).hasSize(1);
  }

  private EatInOrderTable createOrderTableRequest(final String name) {
    final EatInOrderTable orderTable = new EatInOrderTable();
    orderTable.setName(name);
    return orderTable;
  }

  private EatInOrderTable changeNumberOfGuestsRequest(final int numberOfGuests) {
    final EatInOrderTable orderTable = new EatInOrderTable();
    orderTable.setNumberOfGuests(numberOfGuests);
    return orderTable;
  }
}
