package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Price;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @EmbeddedId
    private OrderLineItemSeq seq;

    @Embedded
    @AttributeOverride(
        name = "value",
        column = @Column(name = "menu_id", columnDefinition = "varbinary(16)")
    )
    private MenuId menuId;

    @Embedded
    private Price price;

    @Embedded
    private Quantity quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(
        final OrderLineItemSeq seq,
        final MenuId menuId,
        final Price price,
        final Quantity quantity
    ) {
        this.seq = seq;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuId getMenuId() {
        return this.menuId;
    }

    public Price calculateTotalPrice() {
        return this.price.multiply(this.quantity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderLineItem that = (OrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
