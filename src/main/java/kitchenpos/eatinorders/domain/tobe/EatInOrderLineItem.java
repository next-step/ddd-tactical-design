package kitchenpos.eatinorders.domain.tobe;

import kitchenpos.menus.tobe.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "eatin_order_line_item")
@Entity
public class EatInOrderLineItem {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Price price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected EatInOrderLineItem() {
    }
    public EatInOrderLineItem(UUID id, BigDecimal price, long quantity) {
        this.id = id;
        this.price = new Price(price);
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public long getQuantity() {
        return quantity;
    }
}
