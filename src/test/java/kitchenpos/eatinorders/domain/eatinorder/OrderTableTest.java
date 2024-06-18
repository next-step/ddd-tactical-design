package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.domain.orders.OrderTableStatus;
import kitchenpos.eatinorders.domain.eatinorder.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderTableTest {
  @DisplayName("주문 테이블을 등록할 수 있다.")
  @Test
  void createOrderTable() {
    OrderTable actual = OrderTable.of("udon", 3);

    assertAll(
            () -> assertThat(actual.getCustomerHeadcount()).isEqualTo(3),
            () -> assertThat(actual.getOccupied()).isEqualTo(OrderTableStatus.UNOCCUPIED)
    );
  }

  @DisplayName("빈 테이블을 점유할 수 있다.")
  @Test
  void occupyOrderTable() {
    OrderTable actual = OrderTable.of("udon", 3);

    actual.occupy();

    assertAll(
            () -> assertThat(actual.getOccupied()).isEqualTo(OrderTableStatus.OCCUPIED)
    );
  }

  @DisplayName("빈 테이블로 설정할 수 있다.")
  @Test
  void clearOrderTable() {
    OrderTable actual = OrderTable.of("udon", 3);

    actual.occupy();

    assertThat(actual.getOccupied()).isEqualTo(OrderTableStatus.OCCUPIED);

    actual.unoccupy();

    assertThat(actual.getOccupied()).isEqualTo(OrderTableStatus.UNOCCUPIED);
  }

  @DisplayName("고객 인원를 변경할 수 있다.")
  @ParameterizedTest
  @ValueSource(ints = {1,2,3,4})
  void changeHeadCounts(int headCounts) {
    OrderTable actual = OrderTable.of("udon", 3);

    actual.occupy();
    actual.changeCustomerHeadCounts(headCounts);

    assertThat(actual.getCustomerHeadcount()).isEqualTo(headCounts);
  }

  @DisplayName("빈 테이블은 방문한 고객 인원을 변경할 수 없다.")
  @Test
  void failToChangeHeadCountsWithUnoccupied() {
    OrderTable actual = OrderTable.of("udon", 3);

    assertThat(actual.getOccupied()).isEqualTo(OrderTableStatus.UNOCCUPIED);

    assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> actual.changeCustomerHeadCounts(3));
  }

  @DisplayName("고객 인원은 0명 이상이어야 한다.")
  @ParameterizedTest
  @ValueSource(ints = {-10_000, -1})
  void changeCustomerHeadCountWithNegativePrice(int headCounts) {

    OrderTable actual = OrderTable.of("udon", 3);

    actual.occupy();

    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() ->     actual.changeCustomerHeadCounts(headCounts))
            .withMessageContaining("방문한 손님 수는 0 이상이어야 한다.");
  }
}
