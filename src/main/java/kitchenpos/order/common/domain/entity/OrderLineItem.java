package kitchenpos.order.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.order.common.domain.model.OrderId;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "menu_id"))
    private MenuId menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_id"))
    @JsonBackReference
    private OrderId orderId;

    public OrderLineItem() {}

    public Long getSeq() {
        return seq;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
