package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.order.OrderLineItem;
import kitchenpos.eatinorders.domain.order.OrderLineItems;
import kitchenpos.eatinorders.domain.order.MenuClient;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class DefaultMenuClient implements MenuClient {
    private final MenuRepository menuRepository;

    public DefaultMenuClient(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void validateOrderLineItems(final OrderLineItems orderLineItems) {
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItems.getOrderLineItems().stream()
                        .map(OrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItems.getSize()) {
            throw new IllegalArgumentException("주문한 메뉴의 항목 수량이 등록된 메뉴와 일치하지 않습니다.");
        }

        for (final OrderLineItem orderLineItem : orderLineItems.getOrderLineItems()) {
            final Menu menu = menuRepository.findById(orderLineItem.getMenuId())
                    .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다."));
            if (!menu.isDisplayed()) {
                throw new IllegalStateException("메뉴가 노출상태가 아닙니다.");
            }
            if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
                throw new IllegalArgumentException("메뉴의 가격이 일치하지 않습니다.");
            }
        }
    }
}
