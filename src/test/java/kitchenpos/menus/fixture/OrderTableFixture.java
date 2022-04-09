package kitchenpos.menus.fixture;

import kitchenpos.ordertable.tobe.domain.OrderTable;

public class OrderTableFixture {

  private OrderTableFixture() {
  }

  public static OrderTable buildEmptyNameOrderTable() {
    return new OrderTable(null, 2, true);
  }

  public static OrderTable buildNegativeNumberOfGuestsOrderTable() {
    return new OrderTable("테이블", -2, true);
  }

  public static OrderTable buildReleasedTwoGuestsTable() {
    return new OrderTable("테이블", 2, true);
  }

  public static OrderTable buildUsedTwoGuestsTable() {
    return new OrderTable("테이블", 2, false);
  }

}
