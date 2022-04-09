package kitchenpos.ordertable.tobe.domain;

import static kitchenpos.menus.fixture.OrderTableFixture.buildEmptyNameOrderTable;
import static kitchenpos.menus.fixture.OrderTableFixture.buildNegativeNumberOfGuestsOrderTable;
import static kitchenpos.menus.fixture.OrderTableFixture.buildReleasedTwoGuestsTable;
import static kitchenpos.menus.fixture.OrderTableFixture.buildUsedTwoGuestsTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.ordertable.tobe.domain.service.OrderTableRelatedOrderStatusCheckService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

  @DisplayName("주문테이블의 이름은 빈 값이 아니어야한다.")
  @Test
  void emptyNameTest() {
    //given & when & then
    assertThatThrownBy(() -> buildEmptyNameOrderTable()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("방문 손님 수는 0 미만이 아니어야 한다.")
  @Test
  void negativeNumberOfGuestsTest() {
    //given & when & then
    assertThatThrownBy(() -> buildNegativeNumberOfGuestsOrderTable()).isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("테이블을 사용중으로 설정할 수 있다.")
  @Test
  void toBeUsedTest() {
    //given
    OrderTable releasedTable = buildReleasedTwoGuestsTable();

    //when
    OrderTable result = releasedTable.toBeUsed();

    //then
    assertThat(result.isEmpty()).isFalse();

  }

  @DisplayName("테이블을 비어있음으로 설정할 수 있다.")
  @Test
  void toBeReleasedTest() {
    //given
    OrderTable usedTable = buildUsedTwoGuestsTable();

    //when
    OrderTable result = usedTable.toBeReleased(new RelatedOrderCompletedStatusCheckService());

    //then
    assertThat(result.isEmpty()).isTrue();

  }

  @DisplayName("완료되지 않은 주문이 존재하면 테이블을 비어있음으로 설정할 수 없다.")
  @Test
  void toBeReleasedFailTest() {
    //given
    OrderTable usedTable = buildUsedTwoGuestsTable();

    //when & then
    assertThatThrownBy(() -> usedTable.toBeReleased(new RelatedOrderInCompletedStatusCheckService())).isInstanceOf(
        IllegalStateException.class);
  }

  @DisplayName("방문한 손님 수를 변경할 수 있다.")
  @Test
  void changeNumberOfGuestsTest() {
    //given
    OrderTable twoGuestsTable = buildUsedTwoGuestsTable();
    int toChangeNumber = 3;

    //when
    OrderTable result = twoGuestsTable.changeNumberOfGuests(toChangeNumber);

    //then
    assertThat(result.getNumberOfGuests()).isEqualTo(new NumberOfGuests(toChangeNumber));
  }

  @DisplayName("비어있는 테이블은 방문한 손님 수를 변경할 수 없다.")
  @Test
  void changeNumberOfGuestsFailTest() {
    //given
    OrderTable twoGuestsTable = buildReleasedTwoGuestsTable();
    int toChangeNumber = 3;

    //when & then
    assertThatThrownBy(() -> twoGuestsTable.changeNumberOfGuests(toChangeNumber)).isInstanceOf(
        IllegalStateException.class);
  }


  private static class RelatedOrderCompletedStatusCheckService implements OrderTableRelatedOrderStatusCheckService {

    @Override
    public boolean hasInCompletedOrders(OrderTable orderTable) {
      return false;
    }
  }

  private static class RelatedOrderInCompletedStatusCheckService implements OrderTableRelatedOrderStatusCheckService {

    @Override
    public boolean hasInCompletedOrders(OrderTable orderTable) {
      return true;
    }
  }


}
