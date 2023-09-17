package kitchenpos.ordermaster.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kitchenpos.common.DomainService;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuRepository;

@DomainService
public class MenuFinder {
    private final ToBeMenuRepository menuRepository;

    public MenuFinder(ToBeMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public ToBeOrderLineItems find(final Order request) {

        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<ToBeOrderLineItem> orderLineItem = request.getOrderLineItems().stream()
            .map(it -> {
                ToBeMenu menu = menuRepository.findById(it.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("메뉴가 없으면 등록할 수 없습니다."));
                if (!menu.isDisplayed()) {
                    throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
                }
                if (!menu.isSamePrice(it.getPrice())) {
                    throw new IllegalStateException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 합니다다.");
                }
                return new ToBeOrderLineItem(
                    new OrderMenu(menu.getId(), OrderMenuPrice.of(menu.getPrice().getValue())),
                    OrderQuantity.of(it.getQuantity(), request.getType()));
            })
            .collect(Collectors.toList());

        return new ToBeOrderLineItems(orderLineItem);
    }
}
