package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.domain.vo.OrderLineItems;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.support.ValueObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class EatInOrderLineItemsService extends ValueObject {

    private final MenuRepository menuRepository;


    public EatInOrderLineItemsService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public OrderLineItems getOrderLineItems(List<EatInOrderLineItem> eatInOrderLineItems) {
        validMenuSize(eatInOrderLineItems);
        return new OrderLineItems(eatInOrderLineItems.stream()
                .map(eatInOrderLineItem -> new EatInOrderLineItem(getMenu(eatInOrderLineItem), eatInOrderLineItem.getQuantity(), eatInOrderLineItem.getPrice()))
                .collect(Collectors.toList()));
    }

    private void validMenuSize(List<EatInOrderLineItem> eatInOrderLineItems) {
        final List<Menu> menus = menuRepository.findAllByIdIn(
                eatInOrderLineItems.stream()
                        .map(EatInOrderLineItem::getMenuId)
                        .collect(Collectors.toList()));
        if (menus.size() != eatInOrderLineItems.size()) {
            throw new IllegalArgumentException();
        }
    }


    private Menu getMenu(EatInOrderLineItem orderLineItem) {
        final Menu menu = menuRepository.findById(orderLineItem.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
        return menu;
    }
}
