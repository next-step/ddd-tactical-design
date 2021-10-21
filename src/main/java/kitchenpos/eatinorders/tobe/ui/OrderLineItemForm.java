package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemForm {
    private Long seq;
    private MenuForm menu;
    private Long quantity;
    private BigDecimal price;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public MenuForm getMenu() {
        return menu;
    }

    public UUID getMenuId() {
        return menu.getId();
    }

    public void setMenu(MenuForm menu) {
        this.menu = menu;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static OrderLineItemForm of(TobeOrderLineItem orderLineItem) {
        OrderLineItemForm orderLineItemForm = new OrderLineItemForm();
        orderLineItemForm.setSeq(orderLineItem.getSeq());
        orderLineItemForm.setPrice(orderLineItem.getPrice());
        orderLineItemForm.setQuantity(orderLineItem.getQuantity());
        orderLineItemForm.setMenu(MenuForm.of(orderLineItem.getMenu()));
        return orderLineItemForm;
    }
}
