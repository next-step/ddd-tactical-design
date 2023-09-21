package kitchenpos.eatinorders.domain.orders;

import kitchenpos.menus.tobe.domain.menu.MenuProducts;

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
        validate(orderLineItemMaterials, menuClient);
        return orderLineItemMaterials.stream()
                .map(it -> EatInOrderLineItem.from(it, menuClient))
                .collect(Collectors.collectingAndThen(Collectors.toList(), EatInOrderLineItems::new));
    }

    private static void validate(List<EatInOrderLineItemMaterial> orderLineItemMaterials, MenuClient menuClient) {
        if (Objects.isNull(orderLineItemMaterials) || orderLineItemMaterials.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<UUID> menuIds = orderLineItemMaterials.stream()
                .map(EatInOrderLineItemMaterial::getMenuId)
                .collect(Collectors.toList());
        menuClient.validMenuIds(menuIds);
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
