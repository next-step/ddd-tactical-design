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
        validateEatInOrderLineItem(menuId, quantity, price);
        this.menuId = menuId;
        this.quantity = new EatInOrderLineItemQuantity(quantity);
        this.price = new EatInOrderLineItemPrice(price);
    }

    private void validateEatInOrderLineItem(final UUID menuId, final int quantity, final int price) {
        if (isNull(menuId) || isNull(quantity) || isNull(price)) {
            throw new IllegalArgumentException();
        }
    }

    public UUID menuId() {
        return menuId;
    }
}
