package kitchenpos.eatinorders.domain;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.*;
import kitchenpos.menus.domain.Menu;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "menu_id", length = 16, nullable = false, columnDefinition = "binary(16)")
    private UUID menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Embedded
    private EatInOrderLineItemPrice price;

    public EatInOrderLineItem() {
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public Menu getMenu() {
        return null;
    }

    public void setMenu(final Menu menu) {

    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(final UUID menuId) {
        this.menuId = menuId;
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public void setPrice(final BigDecimal price) {

    }
}
