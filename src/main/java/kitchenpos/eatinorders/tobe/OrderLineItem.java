package kitchenpos.eatinorders.tobe;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Embeddable
@Entity
public class OrderLineItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    private UUID menuId;

    @Transient
    private BigDecimal price;

    protected OrderLineItem() {

    }

    public OrderLineItem(long quantity, UUID menuId, BigDecimal price) {
        validate(quantity, price);
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    private void validate(long quantity, BigDecimal price) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal price() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
