package kitchenpos.eatinorders.tobe.domain.ordertable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegisteredOrderTableTest {

  @DisplayName("주문테이블을 등록할 수 있다.")
  @Test
  public void register() throws Exception {
    OrderTableName name = OrderTableFixture.normalOrderTableName();
    OrderTable orderTable = OrderTableFixture.initial(name);
    assertAll(
        () -> assertThat(orderTable.getId()).isNotNull(),
        () -> assertThat(orderTable.getName()).isEqualTo(name),
        () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(OrderTableNumberOfGuests.ABSENT),
        () -> assertThat(orderTable.isOccupied()).isFalse(),
        () -> assertThat(orderTable.getOrders()).isNull()
    );
  }

  @DisplayName("주문테이블을 사용처리할 수 있다.")
  @Test
  public void sit() throws Exception {
    OrderTable orderTable = OrderTableFixture.normalInitial();
    orderTable.sit();
    assertThat(orderTable.isOccupied()).isTrue();
  }

  @Nested
  @DisplayName("주문테이블을 초기화할 수 있다.")
  class Clear {

    @DisplayName("성공")
    @Test
    public void clear() throws Exception {
      OrderTable orderTable = OrderTableFixture.create(true, null);
      orderTable.clear(true);
      assertAll(
          () -> assertThat(orderTable.isOccupied()).isFalse(),
          () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(
              OrderTableNumberOfGuests.ABSENT)
      );
    }

    @DisplayName("완료되지 않은 주문이 있을 경우 초기화할 수 없다.")
    @Test
    public void becauseIncompeteOrder() throws Exception {
      AcceptedOrder acceptedOrder1 = OrderTableFixture.createAcceptedOrder(
          EatInOrderStatus.COMPLETED);
      AcceptedOrder acceptedOrder2 = OrderTableFixture.createAcceptedOrder(EatInOrderStatus.SERVED);
      AcceptedOrders acceptedOrders = OrderTableFixture.createAcceptedOrders(
          List.of(acceptedOrder1, acceptedOrder2));
      OrderTable orderTable = OrderTableFixture.create(true, acceptedOrders);
      assertThatIllegalStateException()
          .isThrownBy(() -> orderTable.clear(true));
    }
  }

  @DisplayName("주문테이블의 인원을 변경할 수 있다.")
  @Nested
  class ChangeNumberOfGuests {

    @DisplayName("성공")
    @Test
    public void changeNumberOfGuests() throws Exception {
      OrderTableNumberOfGuests changeNumberOfGuests = OrderTableFixture.createOrderTableNumberOfGuests(
          125);
      OrderTable orderTable = OrderTableFixture.create(true, null);
      orderTable.changeNumberOfGuests(changeNumberOfGuests);
      assertThat(orderTable.getNumberOfGuests()).isEqualTo(changeNumberOfGuests);
    }

    @DisplayName("주문테이블은 사용중이어야한다.")
    @Test
    public void becauseNotUse() throws Exception {
      OrderTableNumberOfGuests changeNumberOfGuests = OrderTableFixture.createOrderTableNumberOfGuests(
          125);
      OrderTable orderTable = OrderTableFixture.create(false, null);
      assertThatIllegalStateException()
          .isThrownBy(() -> orderTable.changeNumberOfGuests(changeNumberOfGuests));
    }
  }
}
