package kitchenpos.menus.fixture;

import kitchenpos.eatinorders.tobe.domain.dto.OrderLineItemReq;
import kitchenpos.eatinorders.tobe.domain.dto.MenuRes;

public class OrderLineItemReqFixture {

  private OrderLineItemReqFixture() {}

  public static OrderLineItemReq buildByMenuAndQuantity(MenuRes menu, long quantity) {
    return new OrderLineItemReq(menu.getMenuId(), menu.getMenuPrice(), quantity);
  }

}
