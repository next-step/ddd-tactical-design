package kitchenpos.eatinorders.order.domain.vo;

import java.util.UUID;

public class MenuVo {
    private UUID menuId;
    private Long price;
    private boolean displayed;

    public MenuVo() {
    }

    public MenuVo(UUID menuId, Long price, boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Long getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
