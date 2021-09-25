package kitchenpos.eatinorders.tobe.domain.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderMenus {

    private final Map<UUID, OrderMenu> orderMenus;

    public OrderMenus(final List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus.stream()
                .collect(Collectors.toMap(OrderMenu::getMenuId, Function.identity()));
    }

    public void validate(final List<UUID> menuIds) {
        if (Objects.isNull(menuIds) || menuIds.stream().anyMatch(Predicate.not(orderMenus::containsKey))) {
            throw new IllegalArgumentException("등록된 메뉴가 없습니다.");
        }

        if (orderMenus.values().stream().anyMatch(Predicate.not(OrderMenu::isDisplayed))) {
            throw new IllegalStateException("노출되지 않은 메뉴는 주문할 수 없습니다.");
        }
    }

    public OrderMenu getOrderMenuByMenuId(final UUID menuId) {
        return orderMenus.get(menuId);
    }
}
