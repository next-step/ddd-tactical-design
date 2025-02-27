package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemsException;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderLineItemsValidator {
    private final MenuRepository menuRepository;

    public OrderLineItemsValidator(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void validate(OrderLineItems orderLineItems) {
        List<Menu> menus = menuRepository.findAllByIdIn(orderLineItems.menuIds());

        if (orderLineItems.isSizeMismatch(menus.size())) {
            throw new InvalidOrderLineItemsException("주문 내역에 있는 메뉴가 존재하지 않습니다");
        }

        validateMenus(orderLineItems, menus);
    }

    private void validateMenus(OrderLineItems orderLineItems, List<Menu> menus) {
        for (Menu menu : menus) {
            if (!menu.isDisplayed()) {
                throw new InvalidOrderLineItemsException("노출한 메뉴만 주문 가능합니다");
            }

            if (orderLineItems.hasDifferentPrice(menu.getId(), menu.getPrice())) {
                throw new InvalidOrderLineItemsException("주문한 메뉴의 가격이 실제 메뉴 가격과 다릅니다");
            }
        }
    }
}
