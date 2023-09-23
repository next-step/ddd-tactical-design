package kitchenpos.eatinorders.domain.orders;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Embeddable
public class EatInOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_eat_in_order_line_item_to_eat_in_orders")
    )
    private List<EatInOrderLineItem> orderLineItems;

    protected EatInOrderLineItems() {
    }

    private EatInOrderLineItems(List<EatInOrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public static EatInOrderLineItems from(List<EatInOrderLineItemMaterial> orderLineItemMaterials, MenuClient menuClient) {
        validate(orderLineItemMaterials);
        final OrderedMenus orderedMenus = orderedMenus(orderLineItemMaterials, menuClient);
        return orderLineItemMaterials.stream()
                .map(it -> EatInOrderLineItem.from(it, orderedMenus))
                .collect(Collectors.collectingAndThen(Collectors.toList(), EatInOrderLineItems::new));
    }

    private static OrderedMenus orderedMenus(List<EatInOrderLineItemMaterial> orderLineItemMaterials, MenuClient menuClient) {
        final List<UUID> menuIds = orderLineItemMaterials.stream()
                .map(EatInOrderLineItemMaterial::getMenuId)
                .collect(Collectors.toList());
        return menuClient.getOrderedMenuByMenuIds(menuIds);
    }

    private static void validate(List<EatInOrderLineItemMaterial> orderLineItemMaterials) {
        if (Objects.isNull(orderLineItemMaterials) || orderLineItemMaterials.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 비어있을 수 없습니다. 1개 이상의 주문 항목이 필요합니다.");
        }
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItems that = (EatInOrderLineItems) o;
        return Objects.equals(orderLineItems, that.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderLineItems);
    }
}
