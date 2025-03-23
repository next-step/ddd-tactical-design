package kitchenpos.eatinorders.tobe.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import static java.util.Objects.isNull;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItemQuantity;

import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    private UUID eatInorderId;

    private UUID menuId;

    @Embedded
    private EatInOrderLineItemQuantity quantity;

    @Embedded
    private EatInOrderLineItemPrice price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(final UUID menuId,
                              final int quantity,
                              final int price) {
        validate(menuId, eatInorderId, quantity, price);
        this.menuId = menuId;
        this.quantity = new EatInOrderLineItemQuantity(quantity);
        this.price = new EatInOrderLineItemPrice(price);
    }

    private void validate(final UUID menuId, final UUID eatInorderId, final int quantity, final int price) {
        if (isNull(menuId) || isNull(eatInorderId)  || isNull(quantity) || isNull(price)) {
            throw new IllegalArgumentException();
        }
    }

    public UUID menuId() {
        return menuId;
    }

    public int amount() {
        return price.getPrice() * quantity.getQuantity();
    }

    public UUID getMenuId() {
        return menuId;
    }

    public int getPrice() {
        return price.getPrice();
    }
}
