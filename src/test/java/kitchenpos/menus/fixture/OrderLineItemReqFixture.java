package kitchenpos.menus.fixture;

import kitchenpos.eatinorders.tobe.domain.vo.OrderLineItemReq;
import kitchenpos.eatinorders.tobe.domain.vo.MenuRes;

public class OrderLineItemReqFixture {

  private OrderLineItemReqFixture() {}

  public static OrderLineItemReq buildByMenuAndQuantity(MenuRes menu, long quantity) {
    return new OrderLineItemReq(menu.getMenuId(), menu.getMenuPrice(), quantity);
  }

}
