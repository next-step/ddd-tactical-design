package kitchenpos.eatinorders.tobe.domain.validator;

import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.support.DomainService;

@DomainService
public class OrderLineItemValidator {

    private final MenuRepository menuRepository;

    public OrderLineItemValidator(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void compareToMenuPrice(OrderLineItem orderLineItem) {
        Menu menu = menuRepository.findById(orderLineItem.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        if (!menu.isDisplayed()) {
            throw new IllegalArgumentException("노출중인 메뉴가 아닙니다.");
        }

        if (!menu.getPrice().equals(orderLineItem.getPrice())) {
            throw new IllegalArgumentException("등록된 메뉴의 가격과 주문 항목의 가격이 일치하지 않습니다.");
        }
    }

}
