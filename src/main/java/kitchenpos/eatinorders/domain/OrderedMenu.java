package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * 주문 시점의 메뉴
 */
@Embeddable
public class OrderedMenu extends ValueObject {

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

}
