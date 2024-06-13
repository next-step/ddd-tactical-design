package kitchenpos.eatinorders.application.dto;

import kitchenpos.menus.domain.tobe.menu.Menu;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {

    private Long seq;

    private Menu menu;

    private long quantity;

    private UUID menuId;

    private BigDecimal price;

    public OrderLineItemRequest() {
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(final Menu menu) {
        this.menu = menu;
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
