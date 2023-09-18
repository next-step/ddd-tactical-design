package kitchenpos.ordermaster.domain;

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

    public ToBeOrderLineItems orderLineItemsGenerator(final Order request) {
        validationOfNull(request);
        ToBeMenus menu = findMenu(request.getOrderLineItems());
        List<ToBeOrderLineItem> orderLineItem = request.getOrderLineItems().stream()
            .map(it -> {
                if (menu.hasHiddenMenu()) {
                    throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
                }
                if (menu.isNotMatchByMenuAndPrice(it.getMenuId(), it.getPrice())) {
                    throw new IllegalStateException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 합니다다.");
                }
                return new ToBeOrderLineItem(
                    new OrderMenu(it.getMenuId(), OrderMenuPrice.of(it.getPrice())),
                    OrderQuantity.of(it.getQuantity(), request.getType()));
            })
            .collect(Collectors.toList());
        return new ToBeOrderLineItems(orderLineItem);
    }

    private void validationOfNull(final Order request) {
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private ToBeMenus findMenu(List<OrderLineItem> orderLineItemRequests) {
        List<ToBeMenu> menuList = menuRepository.findAllByIdIn(orderLineItemRequests.stream()
            .map(OrderLineItem::getMenuId)
            .collect(Collectors.toList()));
        return new ToBeMenus(menuList);
    }
}
