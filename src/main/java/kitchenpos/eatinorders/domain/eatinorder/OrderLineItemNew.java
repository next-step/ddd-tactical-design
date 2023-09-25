package kitchenpos.eatinorders.domain.eatinorder;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_line_item_new")
@Entity
public class OrderLineItemNew {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    private UUID menuId;

    private int quantity;

    protected OrderLineItemNew() {
    }

    private OrderLineItemNew(final UUID id, final UUID menuId, final int quantity) {
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static OrderLineItemNew create(final UUID menuId, final int quantity) {
        return new OrderLineItemNew(UUID.randomUUID(), menuId, quantity);
    }

    public UUID getId() {
        return id;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public int getQuantity() {
        return quantity;
    }
}
