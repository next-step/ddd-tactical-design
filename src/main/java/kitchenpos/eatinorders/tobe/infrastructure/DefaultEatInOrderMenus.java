package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenu;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItems;

public class DefaultEatInOrderMenus implements EatInOrderMenus {

    private final EatInOrderMenu eatInOrderMenu;

    public DefaultEatInOrderMenus(final EatInOrderMenu eatInOrderMenu) {
        this.eatInOrderMenu = eatInOrderMenu;
    }

    @Override
    public void validate(final EatInOrderLineItems items) {
        for (EatInOrderLineItem item : items.getItems()) {
            if (!eatInOrderMenu.exists(item.getMenuId())) {
                throw new IllegalArgumentException("주문 항목과 연관된 메뉴가 존재해야 합니다.");
            }

            if (!eatInOrderMenu.isDisplayed(item.getMenuId())) {
                throw new IllegalArgumentException("메뉴가 표시되어야 주문이 가능합니다.");
            }

            if (!eatInOrderMenu.isSamePrice(item.getMenuId(), item.getPrice())) {
                throw new IllegalArgumentException("주문 항목에 있는 가격이 메뉴에 있는 가격과 동일해야합니다.");
            }
        }
    }
}
