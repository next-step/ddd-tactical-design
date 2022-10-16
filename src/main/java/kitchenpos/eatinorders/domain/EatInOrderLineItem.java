package kitchenpos.eatinorders.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "menu_id", length = 16, nullable = false, columnDefinition = "binary(16)")
    private UUID menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Embedded
    private EatInOrderLineItemPrice price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(UUID menuId, long quantity, BigDecimal price) {
        this(null, menuId, quantity, new EatInOrderLineItemPrice(price));
    }

    public EatInOrderLineItem(Long seq, UUID menuId, long quantity, EatInOrderLineItemPrice price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EatInOrderLineItem that = (EatInOrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
