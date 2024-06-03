package kitchenpos.support.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderOrderLineItemPolicy;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "menu_id", nullable = false, columnDefinition = "binary(16)")
    private UUID menuId;

    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(UUID menuId, BigDecimal price, long quantity) {
        this(null, menuId, price, quantity);
    }

    public OrderLineItem(OrderLineItem orderLineItem, MenuClient menuClient) {
        this(orderLineItem.getMenuId(), orderLineItem.getPrice(), orderLineItem.getQuantity());
        EatInOrderOrderLineItemPolicy orderLineItemPolicy = new EatInOrderOrderLineItemPolicy(menuClient);
        orderLineItemPolicy.checkMenu(orderLineItem);
    }

    public OrderLineItem(Long seq, UUID menuId, BigDecimal price, long quantity) {
        this.seq = seq;
        this.menuId = menuId;
        this.price = MenuPrice.from(price);
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price.priceValue();
    }
}
