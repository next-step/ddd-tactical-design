package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableTest {

  @Test
  @DisplayName("주문 테이블을 등록할 수 있다.")
  void create() {
    assertThatNoException()
        .isThrownBy(() -> new OrderTable(UUID.randomUUID(), "9번", 5));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
  void create_NotValidName(final String name) {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> new OrderTable(UUID.randomUUID(), name, 5));
  }

  @DisplayName("빈 테이블을 해지할 수 있다.")
  @Test
  void sit() {
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), "9번");
    orderTable.sit();
    assertThat(orderTable.isOccupied()).isTrue();
  }

  @DisplayName("빈 테이블로 설정할 수 있다.")
  @Test
  void clear() {
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), "9번", 5, true);
    orderTable.clear();
    assertThat(orderTable.isOccupied()).isFalse();
    assertThat(orderTable.getNumberOfGuests()).isEqualTo(OrderTable.EMPTY_GUEST);
  }

  @DisplayName("방문한 손님 수를 변경할 수 있다.")
  @Test
  void changeNumberOfGuests() {
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), "9번", 5, true);
    orderTable.changeNumberOfGuests(4);
    assertThat(orderTable.getNumberOfGuests()).isEqualTo(4);
  }

}