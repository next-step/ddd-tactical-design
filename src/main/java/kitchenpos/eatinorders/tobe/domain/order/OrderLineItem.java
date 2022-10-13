package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "order_id", columnDefinition = "binary(16)", nullable = false)
    private EatInOrder order;

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuId;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Price price;

    protected OrderLineItem() {}

    public OrderLineItem(UUID menuId, Quantity quantity, Price price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLineItem(Long seq, UUID menuId, Quantity quantity, Price price) {
        this(menuId, quantity, price);
        this.seq = seq;
    }

    public void makeRelation(EatInOrder order) {
        this.order = order;
    }

    public BigDecimal totalPrice() {
        return price.getValue().multiply(quantity.toBigDecimal());
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLineItem that = (OrderLineItem) o;

        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
