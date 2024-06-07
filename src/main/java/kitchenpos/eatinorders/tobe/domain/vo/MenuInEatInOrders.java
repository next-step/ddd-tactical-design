package kitchenpos.eatinorders.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuInEatInOrders {
    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Transient
    private boolean isDisplayedMenu;

    @Transient
    private Price price;

    public MenuInEatInOrders(UUID menuId, boolean isDisplayedMenu, BigDecimal price) {
        checkMenuId(menuId);
        checkIsDisplayed(isDisplayedMenu);
        this.menuId = menuId;
        this.isDisplayedMenu = isDisplayedMenu;
        this.price = new Price(price);
    }

    private void checkMenuId(UUID menuId) {
        if (Objects.isNull(menuId)) {
            throw new IllegalArgumentException("메뉴 식별자가 존재하지 않습니다.");
        }
    }

    public void update(BigDecimal price, boolean displayed) {
        checkIsDisplayed(displayed);
        this.price = new Price(price);
        this.isDisplayedMenu = displayed;
    }

    private void checkIsDisplayed(boolean isDisplayedMenu) {
        if (!isDisplayedMenu) {
            throw new IllegalStateException("메뉴가 비공개입니다.");
        }
    }

    public boolean isSameId(UUID inputMenuId) {
        return menuId.equals(inputMenuId);
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Price getPrice() {
        return price;
    }
}
