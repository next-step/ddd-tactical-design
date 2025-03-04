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
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.order.common.application.dto.OrderRequest.OrderLineItemCreate;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItemQty;

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

    @Embedded
    private OrderLineItemQty quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_id"))
    @JsonBackReference
    private OrderId orderId;

    @Transient
    private BigDecimal price;

    public OrderLineItem() {}

    public OrderLineItem(MenuId menuId, OrderId orderId, OrderLineItemQty quantity, BigDecimal price) {
        this.menuId = menuId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public OrderLineItemQty getQuantity() {
        return quantity;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static OrderLineItem fromDto(OrderLineItemCreate dto, OrderId orderId, OrderType type) {
        return new OrderLineItem(MenuId.of(dto.menuId()), orderId, OrderLineItemQty.of(dto.quantity(), type), dto.price());
    }
}
