package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.application.EatInOrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EatInOrderTableServiceTest {
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
    final EatInOrderTableResponse actual = orderTableService.create("1번");
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
    assertThatThrownBy(() -> orderTableService.create(name))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("빈 테이블을 해지할 수 있다.")
  @Test
  void sit() {
    final EatInOrderTable orderTableRequest = createOrderTableRequest("1번");
    final UUID orderTableId = orderTableRepository.save(orderTableRequest).getId();
    final EatInOrderTableResponse actual = orderTableService.sit(orderTableId, 1);
    assertThat(actual.isOccupied()).isTrue();
  }

  @DisplayName("빈 테이블로 설정할 수 있다.")
  @Test
  void clear() {
    final EatInOrderTable orderTableRequest = createOrderTableRequest("1번");
    final UUID orderTableId = orderTableRepository.save(orderTableRequest).getId();
    orderTableService.sit(orderTableId, 1);

    final EatInOrderTableResponse actual = orderTableService.clear(orderTableId);
    assertAll(
        () -> assertThat(actual.getNumberOfGuests()).isZero(),
        () -> assertThat(actual.isOccupied()).isFalse());
  }

  @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
  @Test
  void clearWithUncompletedOrders() {
    final EatInOrderTable orderTableRequest = createOrderTableRequest("1번");
    orderTableRequest.sit(2);
    orderTableRepository.save(orderTableRequest);

    final EatInOrderLineItem eatInOrderLineItem = EatInOrderFixtures.orderLineItem();
    final EatInOrder order =
        EatInOrder.createOrder(Arrays.asList(eatInOrderLineItem), orderTableRequest);
    order.accepted();
    orderRepository.save(order);

    assertThatThrownBy(() -> orderTableService.clear(orderTableRequest.getId()))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("방문한 손님 수를 변경할 수 있다.")
  @Test
  void changeNumberOfGuests() {
    final EatInOrderTable orderTableRequest = createOrderTableRequest("1번");
    orderTableRequest.sit(2);
    orderTableRequest.changeNumberOfGuests(5);
    EatInOrderTable actual = orderTableRepository.save(orderTableRequest);

    assertThat(actual.getNumberOfGuests()).isEqualTo(5);
  }

  @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
  @ValueSource(ints = -1)
  @ParameterizedTest
  void changeNumberOfGuests(final int numberOfGuests) {
    final EatInOrderTable orderTableRequest = createOrderTableRequest("1번");
    orderTableRequest.sit(2);

    assertThatThrownBy(() -> orderTableRequest.changeNumberOfGuests(numberOfGuests))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
  @Test
  void changeNumberOfGuestsInEmptyTable() {
    final EatInOrderTable orderTableRequest = createOrderTableRequest("1번");

    assertThatThrownBy(() -> orderTableRequest.changeNumberOfGuests(2))
        .isInstanceOf(IllegalStateException.class);
  }

  @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    orderTableRepository.save(EatInOrderFixtures.orderTable("1번"));
    final List<EatInOrderTableResponse> actual = orderTableService.findAll();
    assertThat(actual).hasSize(1);
  }

  private EatInOrderTable createOrderTableRequest(String name) {
    return orderTable(name);
  }
}
