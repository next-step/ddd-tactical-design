package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.menus.tobe.domain.model.Menu;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItem {

    private final UUID id;

    private final UUID menuId;

    private final Price price;

    private final long quantity;

    public OrderLineItem(final UUID id, final UUID menuId, final Price price, final long quantity) {
        this.id = id;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(quantity).multiply(price.value());
    }

    public void validateOrderPrice(final Menu menu) {
        if (getPrice().compareTo(menu.getPrice()) != 0) {
            throw new IllegalArgumentException("주문 항목의 가격과 메뉴 가격이 일치하지 않습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public long getQuantity() {
        return quantity;
    }
}
