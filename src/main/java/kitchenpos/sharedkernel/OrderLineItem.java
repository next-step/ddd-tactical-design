package kitchenpos.sharedkernel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Long seq;

    @Transient
    protected UUID menuId;

    @Transient
    protected BigDecimal price;

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
