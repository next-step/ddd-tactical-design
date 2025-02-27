package kitchenpos.eatinorder.domain.model;

import jakarta.persistence.*;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "menu_id", nullable = false, updatable = false)
    private UUID menuId;

    @Column(name = "price", nullable = false, updatable = false)
    private BigDecimal price;

    public OrderLineItem() {
    }

    public static OrderLineItem of(EatInOrderLineItem eatInOrderLineItem) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(eatInOrderLineItem.getSeq());
        orderLineItem.setQuantity(eatInOrderLineItem.getQuantity());
        orderLineItem.setMenuId(eatInOrderLineItem.getMenuId());
        orderLineItem.setPrice(BigDecimal.valueOf(eatInOrderLineItem.getPrice()));
        return orderLineItem;
    }

    public EatInOrderLineItem toDomain(boolean isDisplayedMenu) {
        return EatInOrderLineItem.of(this.seq, this.menuId, this.quantity, this.price.longValue(), isDisplayedMenu);
    }

    public EatInOrderLineItem toDomain() {
        return EatInOrderLineItem.of(this.seq, this.menuId, this.quantity, this.price.longValue());
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
