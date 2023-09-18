package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.Price;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * 주문 시점의 메뉴
 */
@Embeddable
public class OrderedMenu {

    @Column(name = "menu_id", nullable = false)
    private UUID id;

    @Column(name = "menu_name", nullable = false)
    String displayName;

    @Column(name = "menu_prcie", nullable = false)
    BigDecimal menuPrice;

    protected OrderedMenu() {

    }

    public OrderedMenu(UUID id, String displayName, BigDecimal menuPrice) {
        this.id = id;
        this.displayName = displayName;
        this.menuPrice = menuPrice;
    }

    public OrderedMenu(UUID id, String displayName, Price menuPrice) {
        this.id = id;
        this.displayName = displayName;
        this.menuPrice = menuPrice.getValue();
    }
    public UUID getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Price getMenuPrice() {
        return new Price(menuPrice);
    }

    public BigDecimal getMenuPriceValue() {
        return menuPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedMenu)) return false;

        OrderedMenu menu = (OrderedMenu) o;

        if (getId() != null ? !getId().equals(menu.getId()) : menu.getId() != null) return false;
        if (getDisplayName() != null ? !getDisplayName().equals(menu.getDisplayName()) : menu.getDisplayName() != null)
            return false;
        return getMenuPrice() != null ? getMenuPrice().equals(menu.getMenuPrice()) : menu.getMenuPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDisplayName() != null ? getDisplayName().hashCode() : 0);
        result = 31 * result + (getMenuPrice() != null ? getMenuPrice().hashCode() : 0);
        return result;
    }
}
