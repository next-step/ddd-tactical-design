package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "order_line_item")
@Access(AccessType.FIELD)
@Embeddable
public class OrderLineItem {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "menu_id")
    private Long menuId;

    private long quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(Long menuId, int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItem that = (OrderLineItem) o;
        return quantity == that.quantity &&
                Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, quantity);
    }
}
