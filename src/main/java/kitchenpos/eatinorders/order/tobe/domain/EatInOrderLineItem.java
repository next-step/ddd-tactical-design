package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.domain.vo.Price;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "menu_id")
    private UUID menuId;

    @Embedded
    private Price price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected EatInOrderLineItem() {
    }

    private EatInOrderLineItem(final UUID menuId, final Price price, final int quantity) {
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    static EatInOrderLineItem create(final UUID menuId, final Long price, final int quantity) {
        return create(menuId, Price.valueOf(price), quantity);
    }

    static EatInOrderLineItem create(final UUID menuId, final Price price, final int quantity) {
        return new EatInOrderLineItem(menuId, price, quantity);
    }

    public UUID menuId() {
        return menuId;
    }

    public Price price() {
        return price;
    }

    public int quantity() {
        return quantity;
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
