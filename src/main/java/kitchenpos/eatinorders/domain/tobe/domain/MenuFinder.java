package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kitchenpos.common.DomainService;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.ToBeMenus;

@DomainService
public class MenuFinder {
    private final ToBeMenuRepository menuRepository;

    public MenuFinder(ToBeMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public EatInOrderLineItems orderLineItemsGenerator(final Order request) {
        validationOfNull(request);
        ToBeMenus menu = findMenu(request);
        List<EatInOrderLineItem> orderLineItem = request.getOrderLineItems().stream()
            .map(it -> {
                if (menu.hasHiddenMenu()) {
                    throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
                }
                if (menu.isNotMatchByMenuAndPrice(it.getMenuId(), it.getPrice())) {
                    throw new IllegalStateException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 합니다다.");
                }
                return new EatInOrderLineItem(
                    new EatInOrderMenu(it.getMenuId(), EatInOrderMenuPrice.of(it.getPrice())),
                    EatInOrderQuantity.of(it.getQuantity(), request.getType()));
            })
            .collect(Collectors.toList());
        return new EatInOrderLineItems(orderLineItem);
    }

    private void validationOfNull(final Order request) {
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private ToBeMenus findMenu(final Order request) {
        List<ToBeMenu> menuList = menuRepository.findAllByIdIn(request.getOrderLineItems().stream()
            .map(OrderLineItem::getMenuId)
            .collect(Collectors.toList()));
        return new ToBeMenus(menuList);
    }
}
