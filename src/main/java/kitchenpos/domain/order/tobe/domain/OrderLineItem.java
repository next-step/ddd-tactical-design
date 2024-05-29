package kitchenpos.domain.order.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.domain.support.Price;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Embedded
    private Price price;

    protected OrderLineItem() {
    }

    public OrderLineItem(UUID menuId, int quantity, Price price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public int quantity() {
        return quantity;
    }

    public BigDecimal price() {
        return price.getPrice();
    }
}
