package kitchenpos.order.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.util.CollectionUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import kitchenpos.menu.domain.Menu;
import kitchenpos.order.application.dto.OrderLineItemCreationRequest;

@Embeddable
public class OrderLineItems {
    public static final String EMPTY_ORDER_LINE_ITEMS_ERROR = "주문 상품들은 비어있을 수 없습니다.";
    public static final String MISMATCHED_ORDER_LINE_ITEMS_ERROR = "주문 상품과 메뉴 상품이 일치 하지 않습니다.";

    public static final String MENU_NOT_FOUND_ERROR = "메뉴를 찾을 수 없습니다.";

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> value;

    protected OrderLineItems() {
    }

    private OrderLineItems(List<OrderLineItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException(EMPTY_ORDER_LINE_ITEMS_ERROR);
        }

        this.value = items;
    }

    public List<OrderLineItem> getValue() {
        return Collections.unmodifiableList(value);
    }

    private static List<OrderLineItem> buildOrderLineItems(List<OrderLineItemCreationRequest> requests, Map<UUID, Menu> menus) {
        if (menus.size() != requests.size()) {
            throw new IllegalArgumentException(MISMATCHED_ORDER_LINE_ITEMS_ERROR);
        }

        return requests
            .stream()
            .map(request -> {
                Menu menu = menus.get(request.menuId());

                if (menu == null) {
                    throw new NoSuchElementException(MENU_NOT_FOUND_ERROR);
                }

                return new OrderLineItem(
                    request.orderType(),
                    request.price(),
                    request.quantity(),
                    menu
                );
            })
            .toList();
    }


    public static OrderLineItems from(List<OrderLineItem> items) {
        return new OrderLineItems(items);
    }

    public static OrderLineItems fromRequests(List<OrderLineItemCreationRequest> requests, Map<UUID, Menu> menus) {
        List<OrderLineItem> items = buildOrderLineItems(requests, menus);
        return new OrderLineItems(items);
    }

}
